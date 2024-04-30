package ex2.eventLoop.core;

import ex2.gui.ViewListener;
import io.vertx.core.Verticle;

public interface EventLoop extends Verticle {

    void addViewListener(final ViewListener viewListener);

    void searchUrl(final String url, final String word, final int depth);

    void stop();

}
