package ex2.worker;

import ex2.worker.concrete.EventLoopImpl;
import ex2.worker.concrete.VirtualThreadsImpl;
import ex2.worker.concrete.WorkerStrategy;


public interface FactoryWorker {
    LogicWorker createEventLoop();

    LogicWorker createVirtualThreads();

    LogicWorker createRect();

    default LogicWorker createWorker(final WorkerStrategy workerStrategy) {
        return switch (workerStrategy) {
            case EVENT_LOOP -> this.createEventLoop();
            case VIRTUAL_THREAD -> this.createVirtualThreads();
            case REACT -> this.createRect();
        };
    }

    class FactoryWorkerImpl implements FactoryWorker {
        @Override
        public LogicWorker createEventLoop() {
            return new EventLoopImpl();
        }

        @Override
        public LogicWorker createVirtualThreads() {
            return new VirtualThreadsImpl();
        }

        @Override
        public LogicWorker createRect() {
            return new VirtualThreadsImpl();
        }
    }
}
