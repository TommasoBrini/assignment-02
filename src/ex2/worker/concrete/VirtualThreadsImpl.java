package ex2.worker.concrete;

import ex2.core.component.DataEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    public VirtualThreadsImpl() {
        super();
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        // lancio di un virtual threads per ogni dataEvent
        this.executor.submit(() -> {
            this.searchUrl(dataEvent);
        });
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.VIRTUAL_THREAD;
    }
}
