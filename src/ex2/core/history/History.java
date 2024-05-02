package ex2.core.history;

import ex2.core.dataEvent.DataEvent;
import ex2.core.listener.ViewListener;

import java.util.List;

public interface History extends ViewListener {

    void append(final DataEvent dataEvent);

    List<DataEvent> history();

    List<DataEvent> lastHistory();

    void saveJSON();

}
