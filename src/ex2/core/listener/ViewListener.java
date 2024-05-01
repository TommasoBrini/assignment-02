package ex2.core.listener;

import ex2.core.searcher.Searcher;

public interface ViewListener {

    void addInputGuiListener(final InputGuiListener inputGuiListener);

    void onResponse(final Searcher filter);

    void onError(final String message);
}
