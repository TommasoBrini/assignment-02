package ex2.core.listener;

import ex2.core.dataEvent.DataEvent;

public interface ModelListener {

    void onStart(final DataEvent event);

    void onFinish();
}
