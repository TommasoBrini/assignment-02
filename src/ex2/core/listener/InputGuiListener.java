package ex2.core.listener;

import ex2.core.component.searcher.SearcherType;
import ex2.worker.concrete.WorkerStrategy;

public interface InputGuiListener {

    void onSearch(WorkerStrategy workerStrategy, SearcherType searcherType, String site, String word, int maxDepth);

    void onExit();
}
