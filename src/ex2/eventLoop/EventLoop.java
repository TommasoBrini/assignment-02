package ex2.eventLoop;

import ex2.gui.ViewListener;
import io.vertx.core.Verticle;

public interface EventLoop extends Verticle {

    void addViewListener(final ViewListener viewListener);

    void requestWebClient(final String url);

    void stop();

    void addEvent(final Runnable runnable);

    void createServer();

}
