package ex2.worker;

import ex2.worker.concrete.EventLoopImpl;
import ex2.worker.concrete.ReactiveImpl;
import ex2.worker.concrete.VirtualThreadsImpl;


public final class FactoryWorker {

    public static LogicWorker createEventLoop() {
        return new EventLoopImpl();
    }

    public static LogicWorker createVirtualThreads() {
        return new VirtualThreadsImpl();
    }

    public static LogicWorker createRect() {
        return new ReactiveImpl();
    }

    public static LogicWorker createWorker(final LogicWorker.Type workerStrategy) {
        return switch (workerStrategy) {
            case EVENT_LOOP -> createEventLoop();
            case VIRTUAL_THREAD -> createVirtualThreads();
            case REACT -> createRect();
        };
    }
}
