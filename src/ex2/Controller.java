package ex2;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.listener.InputGuiListener;
import ex2.gui.GUISearchWord;
import ex2.worker.WorkerManager;
import ex2.worker.WorkerManagerImpl;

public class Controller implements InputGuiListener {
    final private GUISearchWord gui;
    final private WorkerManager workerManager;

    public Controller() {
        this.gui = new GUISearchWord();
        this.workerManager = new WorkerManagerImpl();

        this.workerManager.addViewListener(this.gui);
        this.gui.addInputGuiListener(this);

        this.onStart();
    }

    public void onStart() {
        this.workerManager.setupWorker(this.gui.getWorkerStrategy());
        this.gui.start(this.workerManager.lastHistory());
    }

    @Override
    public void onSearch(final DataEvent dataEvent) {
        this.workerManager.startSearch(dataEvent);
    }

    @Override
    public void onExit() {
        this.workerManager.stop();
        this.gui.dispose();
    }
}
