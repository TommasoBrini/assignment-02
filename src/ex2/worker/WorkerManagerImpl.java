package ex2.worker;

import ex2.core.dataEvent.DataEvent;
import ex2.core.history.History;
import ex2.core.history.HistoryImpl;
import ex2.core.listener.ViewListener;
import ex2.worker.eventLoop.EventLoopImpl;

import java.util.List;

public class WorkerManagerImpl implements WorkerManager {
    private final LogicWorker worker;
    final private History history;

    public WorkerManagerImpl() {
        this.worker = new EventLoopImpl();
        this.history = new HistoryImpl();
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
    public void searchUrl(final DataEvent dataEvent) {
        this.history.append(dataEvent);
        this.worker.searchUrl(dataEvent);
    }

    @Override
    public void stop() {
        this.history.saveJSON();
        this.worker.stop();
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.worker.addEventUrl(dataEvent);
    }


}
