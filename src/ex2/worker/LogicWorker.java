package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.worker.concrete.WorkerStrategy;

public interface LogicWorker {
    WorkerStrategy strategy();

    void addViewListener(final ViewListener viewListener);

    void addModelListener(final ModelListener modelListener);

    void start(final DataEvent dataEvent);

    void stop();

    void start(final Searcher searcher);

}
