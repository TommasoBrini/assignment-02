package ex2.worker;

import ex2.core.component.Searcher;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;

public interface LogicWorker {
    enum Type {
        EVENT_LOOP,
        VIRTUAL_THREAD,
        REACT
    }

    Type strategy();

    void addViewListener(final ViewListener viewListener);

    void addModelListener(final ModelListener modelListener);

    void start(final Searcher searcher);

    void stop();


}
