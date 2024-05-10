package ex2.core.event;

import io.vertx.core.json.JsonObject;

public interface SearchResponse extends SearchEvent {
    boolean isFinished();

    int countWord();

    JsonObject toJson();
}
