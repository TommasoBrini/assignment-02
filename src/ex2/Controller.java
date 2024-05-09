package ex2;

import ex2.core.component.DataEvent;
import ex2.core.component.History;
import ex2.core.component.concrete.HistoryImpl;
import ex2.core.listener.InputGuiListener;
import ex2.gui.GUIAnalysis;
import ex2.gui.GUISearchWord;
import ex2.web.Server;
import ex2.web.clientManager.ClientManager;
import ex2.web.clientManager.ClientManagerImpl;

import java.util.List;

public class Controller implements InputGuiListener {
    private final Server server;
    private final GUISearchWord gui;
    private final ClientManager clientServiceManager;
    private final History history;
    private final GUIAnalysis guiAnalysis;

    public Controller() {
        this.server = new Server();
        this.gui = new GUISearchWord();
        this.clientServiceManager = new ClientManagerImpl();
        this.guiAnalysis = new GUIAnalysis();
        this.history = new HistoryImpl(List.of(this.guiAnalysis));

        this.clientServiceManager.addModelListener(this.history);
        this.clientServiceManager.addViewListener(this.gui);

        this.gui.addInputGuiListener(this);
        this.gui.setupAnalysis(this.guiAnalysis);
        this.onStart();
    }

    public void onStart() {
        this.server.run();
        this.gui.start(this.history.lastHistory());
    }

    @Override
    public void onSearch(final DataEvent dataEvent) {
        this.clientServiceManager.startSearch(dataEvent);
    }

    @Override
    public void onExit() {
        this.history.saveJSON();
        this.server.stop();
        this.clientServiceManager.stop();
        this.gui.dispose();
        System.exit(0);
    }
}
