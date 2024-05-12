package ex2.core.event;

import ex2.core.component.SearchLogic;
import ex2.worker.LogicWorker;

public interface SearchEvent {
    LogicWorker.Type workerStrategy();
    SearchLogic.Type searchLogicType();
    String url();
    String word();
    int currentDepth();
    int maxDepth();
}
