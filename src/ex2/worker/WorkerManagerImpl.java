package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.component.History;
import ex2.core.component.concreate.HistoryImpl;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.core.component.searcher.SearcherType;
import ex2.server.Server;
import ex2.worker.concreate.EventLoopImpl;

import java.util.List;

public class WorkerManagerImpl implements WorkerManager {
    final private Server server;
    final private History history;
    final private LogicWorker worker;

    public WorkerManagerImpl(final SearcherType searcherType) {
        this.server = new Server();
        this.worker = new EventLoopImpl(searcherType);
        this.history = new HistoryImpl();

        this.worker.addModelListener(this.history);
        this.server.run();
    }

    public WorkerManagerImpl() {
        this(SearcherType.LOCAL);
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
        this.server.stop();
        this.worker.stop();
    }

}
