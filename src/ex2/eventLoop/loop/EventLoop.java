package ex2.eventLoop.loop;

import ex2.eventLoop.dataEvent.DataEvent;
import ex2.listener.ViewListener;
import io.vertx.core.Verticle;

public interface EventLoop extends Verticle {

    void addViewListener(final ViewListener viewListener);

    void searchUrl(final DataEvent dataEvent);

    void stop();

}
