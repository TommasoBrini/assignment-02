package ex2.core.listener;

import ex2.core.event.DataEvent;

public interface ModelListener {

    void onStart(final DataEvent event);

    void onFinish();
}
