package ex2.core.component;

import ex2.core.component.searcher.SearcherType;
import ex2.worker.concrete.WorkerStrategy;
import io.vertx.core.json.JsonObject;

public interface DataEvent {

    WorkerStrategy workerStrategy();

    SearcherType searcherType();

    String url();

    String word();

    int currentDepth();

    int maxDepth();

    long duration();

    JsonObject toJson();
}
