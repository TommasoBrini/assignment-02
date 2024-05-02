package ex2.core.dataEvent;

import io.vertx.core.json.JsonObject;

public interface DataEvent {

    String url();

    String word();

    int currentDepth();

    int maxDepth();

    boolean isOverMaxDepth();

    JsonObject toJson();
}