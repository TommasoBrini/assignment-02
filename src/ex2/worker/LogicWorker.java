package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;

public interface LogicWorker {

    void addViewListener(final ViewListener viewListener);

    void addModelListener(final ModelListener modelListener);

    void startSearch(final DataEvent dataEvent);

    void stop();

}
