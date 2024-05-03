package ex2.core.listener;

import ex2.core.component.searcher.Searcher;

public interface ViewListener {

    void onResponse(final Searcher filter);

    void onError(final String message);

    void onFinish();

}
