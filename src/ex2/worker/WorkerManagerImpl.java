package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.component.History;
import ex2.core.component.concrete.HistoryImpl;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.server.Server;
import ex2.worker.concrete.EventLoopImpl;
import ex2.worker.concrete.VirtualThreadsImpl;

import java.util.List;

public class WorkerManagerImpl implements WorkerManager {
    final private Server server;
    final private History history;
    final private LogicWorker worker;

    public WorkerManagerImpl() {
        this.server = new Server();
        this.worker = new EventLoopImpl();
        //this.worker = new VirtualThreadsImpl();
        this.history = new HistoryImpl();

        this.worker.addModelListener(this.history);
        this.server.run();
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
    public void startSearch(final SearcherType searcherType, final DataEvent dataEvent) {
        this.worker.startSearch(searcherType, dataEvent);
    }

    @Override
    public void stop() {
        this.history.saveJSON();
        this.worker.stop();
        this.server.stop();
        System.exit(0);
    }

}
