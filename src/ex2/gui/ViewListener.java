package ex2.gui;

import ex2.eventLoop.CommandListener;
import ex2.eventLoop.searcher.filter.UrlFilter;

public interface ViewListener {

    void addInputListener(final CommandListener commandListener);

    void onResponse(final UrlFilter filter);

    void onError(final String message);
}
