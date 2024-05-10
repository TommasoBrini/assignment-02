package ex2.worker.concrete;

import ex2.core.event.SearchResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    public VirtualThreadsImpl() {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public Type strategy() {
        return Type.VIRTUAL_THREAD;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.executor.submit(() -> {
            final List<SearchResponse> responses = this.searcher().search(response);
            this.onResponseView(response);
            responses.forEach(this::addEventUrl);
        });
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }
}
