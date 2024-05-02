package ex2.core.history;

import ex2.core.dataEvent.DataEvent;

import java.util.List;

public interface History {

    void append(final DataEvent dataEvent);

    List<DataEvent> history();

    List<DataEvent> lastHistory();

    void saveJSON();

}
