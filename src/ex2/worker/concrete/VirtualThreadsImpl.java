package ex2.worker.concrete;

import ex2.core.component.DataEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ex2.server.client.ClientService;


public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    public VirtualThreadsImpl(final ClientService clientService) {
        super(clientService);
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.executor.submit(() -> this.clientService.searchUrl(dataEvent));
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
