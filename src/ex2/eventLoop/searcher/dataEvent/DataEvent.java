package ex2.eventLoop.searcher.dataEvent;

import io.vertx.core.json.JsonObject;

public interface DataEvent {

    String url();

    String word();

    int currentDepth();

    int maxDepth();

    JsonObject toJson();
}
