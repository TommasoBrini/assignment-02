package ex2;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.InputGuiListener;
import ex2.worker.WorkerManager;
import ex2.worker.WorkerManagerImpl;
import ex2.gui.GUISearchWord;

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
        this.gui.start(this.workerManager.lastHistory());
    }

    @Override
    public void onSearch(final SearcherType searcherType, final String site, final String word, final int maxDepth) {
        final DataEvent dataEvent = new DataEventImpl(site, word, maxDepth, 0);
        this.workerManager.startSearch(searcherType, dataEvent);
    }

    @Override
    public void onExit() {
        this.workerManager.stop();
        this.gui.dispose();
    }
}
