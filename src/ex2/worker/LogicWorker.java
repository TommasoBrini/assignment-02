package ex2.worker;

import ex2.core.dataEvent.DataEvent;
import ex2.core.listener.ViewListener;

public interface LogicWorker {

    void addViewListener(final ViewListener viewListener);

    void searchUrl(final DataEvent dataEvent);

    void start();

    void stop();

}
