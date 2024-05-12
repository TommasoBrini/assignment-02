package ex2.core.event.factory;

import ex2.core.component.SearchLogic;
import ex2.core.event.SearchEvent;
import ex2.worker.LogicWorker;

public final class SearchEventFactory {

    public static SearchEvent create(final LogicWorker.Type workerStrategy,
                                     final SearchLogic.Type searchLogicType,
                                     final String url,
                                     final String word,
                                     final int currentDepth,
                                     final int maxDepth) {
        return new SearchEventImpl(workerStrategy, searchLogicType, url, word, currentDepth, maxDepth);
    }

    private record SearchEventImpl(LogicWorker.Type workerStrategy,
                              SearchLogic.Type searchLogicType,
                              String url,
                              String word,
                              int currentDepth,
                              int maxDepth) implements SearchEvent {
    }
}


