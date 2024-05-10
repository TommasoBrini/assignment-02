package ex2.core.component;

import ex2.core.event.DataEvent;

public interface CounterSearch {

    void increaseConsumeIfMaxDepth(final DataEvent dataEvent);
    void increaseSendIfMaxDepth(final Searcher searcher);
    boolean isEnd();
    void reset();
}
