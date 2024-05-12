package ex2.core.listener;

import ex2.core.event.SearchEvent;

public interface InputGuiListener {

    void onSearch(final SearchEvent searchEvent);

    void onExit();
}
