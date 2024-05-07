package ex2.core.component;

import ex2.core.component.searcher.Searcher;

public interface CounterSearch {

    void increaseConsumeIfMaxDepth(final DataEvent dataEvent);
    void increaseSendIfMaxDepth(final Searcher searcher);
    boolean isEnd();
    void reset();
}
