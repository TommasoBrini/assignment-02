package ex2.core.event;

import ex2.core.component.SearchLogic;
import ex2.worker.LogicWorker;
import io.vertx.core.json.JsonObject;

public interface DataEvent {

    LogicWorker.Type workerStrategy();

    SearchLogic.Type searcherType();

    String url();

    String word();

    int currentDepth();

    int maxDepth();

    long duration();

    JsonObject toJson();
}
