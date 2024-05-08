package ex2.core.listener;

import ex2.core.component.DataEvent;

public interface HistoryListener {

    void append(final DataEvent event);

}
