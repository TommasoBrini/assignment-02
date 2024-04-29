package ex2.gui;

import ex2.eventLoop.filter.UrlFilter;

public interface ViewListener {

    void onResponse(final UrlFilter urlFilter);

    void onError(final String message);
}
