package ex2.eventLoop;

import ex2.eventLoop.core.EventLoop;
import ex2.eventLoop.core.EventLoopImpl;
import ex2.gui.GUISearchWord;
import ex2.gui.area.CommandArea;

public class Controller implements CommandListener {
    final private GUISearchWord gui;
    final private EventLoop eventLoop;

    public Controller() {
        this.gui = new GUISearchWord();
        this.eventLoop = new EventLoopImpl();
        this.eventLoop.addViewListener(this.gui);
        this.gui.addInputListener(this);
    }


    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int depth) {
        this.eventLoop.searchUrl(site, word, depth);
    }

    @Override
    public void onExit() {
        this.eventLoop.stop();
        this.gui.dispose();
    }
}
