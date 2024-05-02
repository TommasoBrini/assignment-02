package ex2.core.history;

import ex2.core.dataEvent.DataEvent;
import ex2.core.listener.ModelListener;

import java.util.List;

public interface History extends ModelListener {

    List<DataEvent> history();

    List<DataEvent> lastHistory();

    void saveJSON();

}
