package ex2.core.listener;

import ex2.core.event.SearchResponse;

public interface ViewListener {

    void onResponse(final SearchResponse response);

    void onError(final String message);

    void onFinish();

}
