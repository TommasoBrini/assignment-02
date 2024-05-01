package ex2.eventLoop;

import ex2.core.dataEvent.DataEvent;
import ex2.core.listener.ViewListener;
import io.vertx.core.Verticle;

public interface EventLoop extends Verticle {

    void addViewListener(final ViewListener viewListener);

    void searchUrl(final DataEvent dataEvent);

    void stop();

}
