package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.worker.concrete.WorkerStrategy;

import java.util.List;

public interface WorkerManager {

    List<DataEvent> lastHistory();

    void setupWorker(final WorkerStrategy workerStrategy);

    void startSearch(final DataEvent dataEvent);

    void addViewListener(final ViewListener viewListener);

    void addModelListener(final ModelListener modelListener);

    void stop();
}
