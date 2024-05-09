package ex2.web.clientManager;

import ex2.core.component.DataEvent;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;

public interface ClientManager {

//    void setupWorker(final WorkerStrategy workerStrategy);

    void startSearch(final DataEvent dataEvent);

    void addViewListener(final ViewListener viewListener);

    void addModelListener(final ModelListener modelListener);

    void stop();
}
