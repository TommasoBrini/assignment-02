package ex2.eventLoop.core;

import ex2.eventLoop.searcher.dataEvent.DataEvent;

public interface WorkerLoop {

    void addEventUrl(final DataEvent dataEvent);

}
