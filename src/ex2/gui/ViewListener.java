package ex2.gui;

import ex2.eventLoop.CommandListener;
import ex2.eventLoop.searcher.Searcher;

public interface ViewListener {

    void addInputListener(final CommandListener commandListener);

    void onResponse(final Searcher filter);

    void onError(final String message);
}
