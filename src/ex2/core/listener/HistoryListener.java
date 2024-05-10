package ex2.core.listener;

import ex2.core.event.DataEvent;

public interface HistoryListener {

    void append(final DataEvent event);

}
