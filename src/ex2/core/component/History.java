package ex2.core.component;

import ex2.core.event.DataEvent;
import ex2.core.listener.HistoryListener;
import ex2.core.listener.ModelListener;

import java.util.List;

public interface History extends ModelListener {

    void addListener(final HistoryListener listener);

    List<DataEvent> history();

    List<DataEvent> lastHistory();

    void saveJSON();

}
