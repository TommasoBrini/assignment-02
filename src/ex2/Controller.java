package ex2;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import ex2.core.listener.InputGuiListener;
import ex2.worker.WorkerManager;
import ex2.worker.WorkerManagerImpl;
import ex2.gui.GUISearchWord;
import ex2.gui.area.CommandArea;
import ex2.server.Server;

public class Controller implements InputGuiListener {
    final private Server server;
    final private GUISearchWord gui;
    final private WorkerManager workerManager;

    public Controller() {
        this.server = new Server();
        this.gui = new GUISearchWord();
        this.workerManager = new WorkerManagerImpl();

        this.workerManager.addViewListener(this.gui);
        this.gui.addInputGuiListener(this);

        this.onStart();
    }

    public void onStart() {
        this.server.run();
        this.gui.start(this.workerManager.lastHistory());
    }

    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int maxDepth) {
        final DataEvent dataEvent = new DataEventImpl(site, word, maxDepth, 0);
        this.workerManager.startSearch(dataEvent);
    }

    @Override
    public void onExit() {
        this.server.stop();
        this.workerManager.stop();
        this.gui.dispose();
    }
}
