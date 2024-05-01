package ex2.core.listener;

import ex2.gui.area.CommandArea;

public interface InputGuiListener {

    void onSearch(CommandArea commandArea, String site, String word, int maxDepth);

    void onExit();
}
