package ex2.worker;

import ex2.core.dataEvent.DataEvent;
import ex2.core.history.History;
import ex2.core.history.HistoryImpl;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.worker.eventLoop.EventLoopImpl;

import java.util.List;

public class WorkerManagerImpl implements WorkerManager {
    private final LogicWorker worker;
    final private History history;

    public WorkerManagerImpl() {
        this.worker = new EventLoopImpl();
        this.history = new HistoryImpl();

        this.worker.addModelListener(this.history);
    }

    @Override
    public List<DataEvent> lastHistory() {
        return this.history.lastHistory();
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.worker.addViewListener(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.worker.addModelListener(modelListener);
    }

    @Override
    public void startSearch(final DataEvent dataEvent) {
        this.worker.startSearch(dataEvent);
    }

    @Override
    public void stop() {
        this.history.saveJSON();
        this.worker.stop();
    }

}
