package ex2;

import ex2.core.component.DataEvent;
import ex2.core.component.History;
import ex2.core.component.concrete.HistoryImpl;
import ex2.core.listener.InputGuiListener;
import ex2.gui.GUISearchWord;
import ex2.gui.GUIAnalysis;
import ex2.worker.WorkerManager;
import ex2.worker.WorkerManagerImpl;

import java.util.List;

public class Controller implements InputGuiListener {
    private final History history;
    private final GUIAnalysis guiAnalysis;
    private final GUISearchWord gui;
    private final WorkerManager workerManager;

    public Controller() {
        this.gui = new GUISearchWord();
        this.guiAnalysis = new GUIAnalysis();
        this.history = new HistoryImpl(List.of(this.guiAnalysis));
        this.workerManager = new WorkerManagerImpl();

        this.workerManager.addModelListener(this.history);
        this.workerManager.addViewListener(this.gui);
        this.gui.addInputGuiListener(this);

        this.onStart();
    }

    public void onStart() {
        this.workerManager.setupWorker(this.gui.getWorkerStrategy());
        this.gui.start(this.history.lastHistory());
    }

    @Override
    public void onSearch(final DataEvent dataEvent) {
        this.workerManager.startSearch(dataEvent);
    }

    @Override
    public void onExit() {
        this.history.saveJSON();
        this.workerManager.stop();
        this.gui.dispose();
        System.exit(0);
    }
}
