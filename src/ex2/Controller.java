package ex2;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import ex2.core.history.History;
import ex2.core.history.HistoryImpl;
import ex2.core.listener.InputGuiListener;
import ex2.eventLoop.EventLoop;
import ex2.eventLoop.EventLoopImpl;
import ex2.gui.GUISearchWord;
import ex2.gui.area.CommandArea;

public class Controller implements InputGuiListener {
    final private GUISearchWord gui;
    final private History history;
    final private EventLoop eventLoop;

    public Controller() {
        this.history = new HistoryImpl();
        this.gui = new GUISearchWord();
        this.eventLoop = new EventLoopImpl();

        this.eventLoop.addViewListener(this.history);
        this.eventLoop.addViewListener(this.gui);

        this.gui.addInputGuiListener(this);

        this.onStart();
    }

    public void onStart() {
        this.gui.start(this.history.lastHistory());
    }

    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int maxDepth) {
        final DataEvent dataEvent = new DataEventImpl(site, word, maxDepth, 0);
        this.eventLoop.searchUrl(dataEvent);
    }

    @Override
    public void onExit() {
        this.history.saveJSON();
        this.eventLoop.stop();
        this.gui.dispose();
    }
}
