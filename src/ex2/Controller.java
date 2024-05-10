package ex2;

import ex2.core.component.History;
import ex2.core.component.Searcher;
import ex2.core.component.concrete.HistoryImpl;
import ex2.core.component.concrete.SearcherImpl;
import ex2.core.listener.InputGuiListener;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.gui.GUIAnalysis;
import ex2.gui.GUISearchWord;
import ex2.core.event.SearchEvent;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import ex2.worker.FactoryWorker;
import ex2.worker.LogicWorker;

import java.util.ArrayList;
import java.util.List;

public class Controller implements InputGuiListener {
    // WEB
    private final Server server;
    private final ClientService clientService;
    // MODEL
    private final History history;
    private final Searcher searcher;
    private LogicWorker logicWorker;
    // VIEW
    private final GUISearchWord gui;
    private final GUIAnalysis guiAnalysis;
    // LISTENER
    private final List<ModelListener> modelListeners;
    private final List<ViewListener> viewListeners;

    public Controller() {
        // WEB
        this.server = new Server();
        this.clientService = ClientServiceFactory.createJsoup();
        // GUI
        this.gui = new GUISearchWord();
        this.guiAnalysis = new GUIAnalysis();
        // MODEL
        this.searcher = new SearcherImpl();
        this.history = new HistoryImpl(List.of(this.guiAnalysis));
        // LISTENER
        this.modelListeners = new ArrayList<>();
        this.viewListeners = new ArrayList<>();

        this.viewListeners.add(this.gui);
        this.modelListeners.add(this.history);

        this.onStart();
    }

    public void onStart() {
        this.server.run();
        this.gui.start(this.history.lastHistory());
    }

    @Override
    public void onSearch(final SearchEvent searchEvent) {
        this.logicWorker = FactoryWorker.createWorker(searchEvent.workerStrategy());
        this.viewListeners.forEach(this.logicWorker::addViewListener);
        this.modelListeners.forEach(this.logicWorker::addModelListener);
        this.searcher.setup(this.clientService, searchEvent.url(), searchEvent.searchLogicType(), searchEvent.word());
        this.logicWorker.start(this.searcher);
    }

    @Override
    public void onExit() {
        // TODO
        this.logicWorker.stop();
        this.history.saveJSON();
        this.server.stop();
        this.gui.dispose();
        this.guiAnalysis.dispose();
        System.exit(0);
    }
}
