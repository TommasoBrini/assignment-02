package ex2.core.component;

import ex2.core.event.SearchData;
import ex2.core.listener.HistoryListener;
import ex2.core.listener.ModelListener;

import java.util.List;

public interface History extends ModelListener {

    void addListener(final HistoryListener listener);

    List<SearchData> history();

    List<SearchData> lastHistory();

    void saveJSON();

}
