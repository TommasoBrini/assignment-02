package ex2.worker;

import ex2.core.dataEvent.DataEvent;
import ex2.core.listener.ViewListener;
import ex2.core.searcher.SearcherWorker;

public interface LogicWorker extends SearcherWorker {

    void addViewListener(final ViewListener viewListener);

    void searchUrl(final DataEvent dataEvent);

    void stop();
}
