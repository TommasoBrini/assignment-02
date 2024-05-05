package ex2.core.listener;

import ex2.core.component.DataEvent;

public interface InputGuiListener {

    void onSearch(final DataEvent dataEvent);

    void onExit();
}
