package ex2;

import ex2.core.component.DataEvent;
import ex2.core.component.History;
import ex2.core.component.concrete.HistoryImpl;
import ex2.core.component.searcher.SearchLogic;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherImpl;
import ex2.core.listener.InputGuiListener;
import ex2.gui.GUIAnalysis;
import ex2.gui.GUISearchWord;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import ex2.web.clientManager.ClientManager;
import ex2.web.clientManager.ClientManagerImpl;
import ex2.worker.LogicWorker;

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

    public Controller() {
        // WEB
        this.server = new Server();
        this.clientService = ClientServiceFactory.createJsoup();
        // MODEL
        this.searcher = new SearcherImpl();
        // GUI
        this.gui = new GUISearchWord();
        this.guiAnalysis = new GUIAnalysis();
        // HISTORY
        this.history = new HistoryImpl(List.of(this.guiAnalysis));
    }

    public void onStart() {
        this.server.run();
        this.gui.start(this.history.lastHistory());
    }

    @Override
    public void onSearch(final DataEvent dataEvent) {
        this.searcher.setup(this.clientService, SearchLogic.Type.REMOTE, dataEvent.url(), dataEvent.word());
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
