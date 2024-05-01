package ex2.eventLoop.searcher;

import ex2.eventLoop.dataEvent.DataEvent;

public interface WorkerLoop {

    void addEventUrl(final DataEvent dataEvent);

}
