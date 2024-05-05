package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.component.History;
import ex2.core.component.concrete.HistoryImpl;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.server.Server;
import ex2.worker.concrete.WorkerStrategy;

import java.util.ArrayList;
import java.util.List;

public class WorkerManagerImpl implements WorkerManager {
    private final Server server;
    private final History history;
    private final List<ViewListener> viewListeners;
    private final  List<ModelListener> modelListeners;

    private final FactoryWorker factoryWorker;
    private LogicWorker worker;

    public WorkerManagerImpl() {
        this.server = new Server();
        this.history = new HistoryImpl();
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
        this.factoryWorker = new FactoryWorker.FactoryWorkerImpl();

        this.addModelListener(this.history);
        this.server.run();
    }

    @Override
    public List<DataEvent> lastHistory() {
        return this.history.lastHistory();
    }

    @Override
    public void startSearch(final WorkerStrategy workerStrategy, final SearcherType searcherType, final DataEvent dataEvent) {
        this.worker = this.factoryWorker.createWorker(workerStrategy);
        this.modelListeners.forEach(this.worker::addModelListener);
        this.viewListeners.forEach(this.worker::addViewListener);
        this.worker.startSearch(searcherType, dataEvent);
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.modelListeners.add(modelListener);
    }

    @Override
    public void stop() {
        this.history.saveJSON();
        this.worker.stop();
        this.server.stop();
        System.exit(0);
    }

}
