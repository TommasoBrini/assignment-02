package ex2.core.listener;

import ex2.core.component.Searcher;
import ex2.core.event.SearchResponse;

public interface ModelListener {

    void onStart(final SearchResponse response);

    void onFinish(final Searcher searcher);
}
