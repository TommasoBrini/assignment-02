package ex2.eventLoop;

import ex2.eventLoop.core.EventLoop;
import ex2.eventLoop.core.EventLoopImpl;
import ex2.gui.SearchWord;
import ex2.gui.ViewListener;

public class Runner {
    final private ViewListener gui;
    final private EventLoop eventLoop;

    public Runner() {
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



}
