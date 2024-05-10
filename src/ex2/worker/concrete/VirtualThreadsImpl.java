package ex2.worker.concrete;

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
    public void addEventUrl(final String url) {
        this.executor.submit(() -> this.searcher().search(url));
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }
}
