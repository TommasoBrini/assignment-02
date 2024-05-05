package ex2.core.component;

import ex2.core.listener.ModelListener;

import java.util.List;

public interface History extends ModelListener {

    List<DataEvent> history();

    List<DataEvent> lastHistory();

    void saveJSON();

}
