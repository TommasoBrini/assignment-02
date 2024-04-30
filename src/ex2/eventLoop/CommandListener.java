package ex2.eventLoop;

import ex2.gui.area.CommandArea;

public interface CommandListener {

    void onSearch(CommandArea commandArea, String site, String word, int maxDepth);

    void onExit();
}
