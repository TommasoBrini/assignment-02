package ex2.worker.concrete;

import ex2.core.component.DataEvent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;

import ex2.core.component.searcher.Searcher;
import okhttp3.*;


public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    private final OkHttpClient okHttpClient;

    public VirtualThreadsImpl() {
        super();
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.executor.submit(() -> {
            this.searchUrl(dataEvent);
        });
    }

    @Override
    protected void searchUrl(final DataEvent dataEvent) {
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
                        final Searcher searcher = VirtualThreadsImpl.this.searcherFactory.create(VirtualThreadsImpl.this.searcherType, VirtualThreadsImpl.this, dataEvent, responseBody.string(), duration);
                        VirtualThreadsImpl.this.viewListeners.forEach(listener -> listener.onResponse(searcher));
                        VirtualThreadsImpl.this.counterSearch.increaseSendIfMaxDepth(searcher);
                        searcher.addSearchFindUrls();
                    }
                    VirtualThreadsImpl.this.counterSearch.increaseConsumeIfMaxDepth(dataEvent);

                    if (VirtualThreadsImpl.this.counterSearch.isEnd()) {
                        VirtualThreadsImpl.this.modelListeners.forEach(ModelListener::onFinish);
                        VirtualThreadsImpl.this.viewListeners.forEach(ViewListener::onFinish);
                    }
                } catch (final IOException e) {
                    System.out.println("ERROR -> " + e.getMessage());
                    VirtualThreadsImpl.this.viewListeners.forEach(viewListener -> viewListener.onError(e.getMessage()));
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e) {
                System.out.println("ERROR -> " + e.getMessage());
                VirtualThreadsImpl.this.viewListeners.forEach(viewListener -> viewListener.onError(e.getMessage()));
            }
        });
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.VIRTUAL_THREAD;
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }

}
