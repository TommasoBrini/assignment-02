package ex2.core.listener;

import ex2.core.component.searcher.SearcherType;

public interface InputGuiListener {

    void onSearch(SearcherType searcherType, String site, String word, int maxDepth);

    void onExit();
}
