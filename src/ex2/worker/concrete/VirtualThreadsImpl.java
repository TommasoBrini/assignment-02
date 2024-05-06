package ex2.worker.concrete;

import ex2.core.component.DataEvent;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;

import ex2.core.component.searcher.Searcher;
import okhttp3.*;


public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    private final OkHttpClient okHttpClient;

    public VirtualThreadsImpl() {
        super();
        this.okHttpClient = new OkHttpClient();
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.executor.submit(() -> {
            this.searchUrl(dataEvent);
        });
    }

    @Override
    protected void searchUrl(DataEvent dataEvent) {
        final long startTime = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(dataEvent.url())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response){
                final long duration = System.currentTimeMillis() - startTime;
                final int statusCode = response.code();
                try (ResponseBody responseBody = response.body()) {
                    if (statusCode >= STATUS_CODE_MIN && statusCode < STATUS_CODE_MAX && responseBody != null) {
                        System.out.println(response.message() + " " + dataEvent.url());
                        final Searcher searcher = searcherFactory.create(searcherType, VirtualThreadsImpl.this, dataEvent, responseBody.string(), duration);
                        viewListeners.forEach(listener -> listener.onResponse(searcher));
                        counterSearch.increaseSendIfMaxDepth(searcher);
                        searcher.addSearchFindUrls();
                    }
                    counterSearch.increaseConsumeIfMaxDepth(dataEvent);

                    if (counterSearch.isEnd()) {
                        modelListeners.forEach(ModelListener::onFinish);
                        viewListeners.forEach(ViewListener::onFinish);
                    }
                } catch (IOException e) {
                    System.out.println("ERROR -> " + e.getMessage());
                    viewListeners.forEach(viewListener -> viewListener.onError(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("ERROR -> " + e.getMessage());
                viewListeners.forEach(viewListener -> viewListener.onError(e.getMessage()));
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
