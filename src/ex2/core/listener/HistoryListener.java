package ex2.core.listener;

import ex2.core.event.SearchData;

public interface HistoryListener {

    void append(final SearchData event);

}
