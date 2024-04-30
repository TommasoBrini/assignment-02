package ex2.eventLoop;

import ex2.eventLoop.core.EventLoop;
import ex2.eventLoop.core.EventLoopImpl;
import ex2.gui.SearchWord;
import ex2.gui.ViewListener;
import ex2.gui.area.CommandArea;

public class Controller implements CommandListener {
    final private ViewListener gui;
    final private EventLoop eventLoop;

    public Controller() {
        this.gui = new SearchWord();
        this.eventLoop = new EventLoopImpl();
        this.eventLoop.addViewListener(this.gui);
    }

    public void stop() {
        this.eventLoop.stop();
    }

    public void requestWebClient(final String url) {
        this.eventLoop.requestWebClient(url);
    }

    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int depth) {

    }

    @Override
    public void onStop() {
        this.stop();
    }
}
