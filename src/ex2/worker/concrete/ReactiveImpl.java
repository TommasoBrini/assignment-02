package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReactiveImpl extends AbstractWorker {
    private final Subject<DataEvent> subject;

    private final OkHttpClient okHttpClient;

    public ReactiveImpl() {
        super();
        this.okHttpClient = new OkHttpClient();
        this.subject = PublishSubject.create();
        this.subject.observeOn(Schedulers.computation()).subscribe(this::searchUrl);
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.subject.onNext(dataEvent);
    }

    @Override
    protected void searchUrl(DataEvent dataEvent) {
        final long startTime = System.currentTimeMillis();
        final Request request = new Request.Builder()
                .url(dataEvent.url())
                .build();
        this.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(final Call call, final Response response){
                final long duration = System.currentTimeMillis() - startTime;
                final int statusCode = response.code();
                try (final ResponseBody responseBody = response.body()) {

                    if (statusCode >= STATUS_CODE_MIN && statusCode < STATUS_CODE_MAX && responseBody != null) {
                        System.out.println(response.message() + " " + dataEvent.url());
                        final Searcher searcher = ReactiveImpl.this.searcherFactory.create(ReactiveImpl.this.searcherType, ReactiveImpl.this, dataEvent, responseBody.string(), duration);
                        ReactiveImpl.this.viewListeners.forEach(listener -> listener.onResponse(searcher));
                        ReactiveImpl.this.counterSearch.increaseSendIfMaxDepth(searcher);
                        searcher.addSearchFindUrls();
                    }

                    ReactiveImpl.this.counterSearch.increaseConsumeIfMaxDepth(dataEvent);

                    if (ReactiveImpl.this.counterSearch.isEnd()) {
                        ReactiveImpl.this.modelListeners.forEach(ModelListener::onFinish);
                        ReactiveImpl.this.viewListeners.forEach(ViewListener::onFinish);
                    }
                } catch (final IOException e) {
                    System.out.println("ERROR -> " + e.getMessage());
                    ReactiveImpl.this.viewListeners.forEach(viewListener -> viewListener.onError(e.getMessage()));
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                System.out.println("ERROR -> " + e.getMessage());
                ReactiveImpl.this.viewListeners.forEach(viewListener -> viewListener.onError(e.getMessage()));
            }
        });
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.REACT;
    }

    @Override
    public void stop() {
        this.subject.onComplete();
    }
}
