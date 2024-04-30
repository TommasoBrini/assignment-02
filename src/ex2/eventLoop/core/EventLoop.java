package ex2.eventLoop.core;

import ex2.eventLoop.searcher.dataEvent.DataEvent;
import ex2.gui.ViewListener;
import io.vertx.core.Verticle;

public interface EventLoop extends Verticle {

    void addViewListener(final ViewListener viewListener);

    void searchUrl(final DataEvent dataEvent);

    void stop();

}
