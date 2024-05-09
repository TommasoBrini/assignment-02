package ex2.worker.concrete;

import ex2.core.component.DataEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ex2.core.component.searcher.Searcher;
import ex2.web.client.ClientService;


public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    public VirtualThreadsImpl() {
        super(clientService);
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.executor.submit(() -> this.clientService.findUrl(dataEvent.url()));
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.VIRTUAL_THREAD;
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }

    @Override
    public void start(final Searcher searcher) {

    }
}
