package ex2;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import ex2.core.listener.InputGuiListener;
import ex2.worker.WorkerManagerImpl;
import ex2.gui.GUISearchWord;
import ex2.gui.area.CommandArea;

public class Controller implements InputGuiListener {
    final private GUISearchWord gui;
    final private WorkerManagerImpl workerManager;

    public Controller() {
        this.gui = new GUISearchWord();
        this.workerManager = new WorkerManagerImpl();

        this.workerManager.addViewListener(this.gui);
        this.gui.addInputGuiListener(this);

        this.onStart();
    }

    public void onStart() {
        this.gui.start(this.workerManager.lastHistory());
    }

    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int maxDepth) {
        final DataEvent dataEvent = new DataEventImpl(site, word, maxDepth, 0);
        this.workerManager.searchUrl(dataEvent);
    }

    @Override
    public void onExit() {
        this.workerManager.stop();
        this.gui.dispose();
    }
}
