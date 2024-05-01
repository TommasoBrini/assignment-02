package ex2.listener;

import ex2.eventLoop.searcher.Searcher;

public interface ViewListener {

    void addInputGuiListener(final InputGuiListener inputGuiListener);

    void onResponse(final Searcher filter);

    void onError(final String message);
}
