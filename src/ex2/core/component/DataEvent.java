package ex2.core.component;

import io.vertx.core.json.JsonObject;

public interface DataEvent {

    String url();

    String word();

    int currentDepth();

    int maxDepth();

    long duration();

    JsonObject toJson();
}
