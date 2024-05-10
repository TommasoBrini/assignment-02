package ex2.core.component;

import ex2.core.event.SearchData;

public interface CounterSearch {

    void increaseConsumeIfMaxDepth(final SearchData searchData);
    void increaseSendIfMaxDepth(final Searcher searcher);
    boolean isEnd();
    void reset();
}
