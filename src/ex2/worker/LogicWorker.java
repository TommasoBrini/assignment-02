package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.web.client.ClientListener;
import ex2.worker.concrete.WorkerStrategy;

public interface LogicWorker extends ClientListener {
    WorkerStrategy strategy();

    void addViewListener(final ViewListener viewListener);

    void addModelListener(final ModelListener modelListener);

    void start(final DataEvent dataEvent);

    void stop();

}
