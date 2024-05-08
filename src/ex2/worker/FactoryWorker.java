package ex2.worker;

import ex2.server.client.ClientService;
import ex2.worker.concrete.EventLoopImpl;
import ex2.worker.concrete.ReactiveImpl;
import ex2.worker.concrete.VirtualThreadsImpl;
import ex2.worker.concrete.WorkerStrategy;


public interface FactoryWorker {
    LogicWorker createEventLoop(final ClientService clientService);

    LogicWorker createVirtualThreads(final ClientService clientService);

    LogicWorker createRect(final ClientService clientService);

    default LogicWorker createWorker(final ClientService clientService, final WorkerStrategy workerStrategy) {
        return switch (workerStrategy) {
            case EVENT_LOOP -> this.createEventLoop(clientService);
            case VIRTUAL_THREAD -> this.createVirtualThreads(clientService);
            case REACT -> this.createRect(clientService);
        };
    }

    class FactoryWorkerImpl implements FactoryWorker {
        @Override
        public LogicWorker createEventLoop(final ClientService clientService) {
            return new EventLoopImpl(clientService);
        }

        @Override
        public LogicWorker createVirtualThreads(final ClientService clientService) {
            return new VirtualThreadsImpl(clientService);
        }

        @Override
        public LogicWorker createRect(final ClientService clientService) {
            return new ReactiveImpl(clientService);
        }
    }
}
