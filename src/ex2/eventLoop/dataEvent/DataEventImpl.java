package ex2.eventLoop.dataEvent;

import io.vertx.core.json.JsonObject;

public record DataEventImpl(String url, String word, int maxDepth, int currentDepth) implements DataEvent {

    private static final String URL = "url";
    private static final String WORD = "word";
    private static final String MAX_DEPTH = "maxDepth";
    private static final String CURRENT_DEPTH = "currentDepth";

    public DataEventImpl(final JsonObject jsonObject) {
        this(jsonObject.getString(URL),
                jsonObject.getString(WORD),
                jsonObject.getInteger(MAX_DEPTH),
                jsonObject.getInteger(CURRENT_DEPTH));
    }

    public boolean isMaxDepth() {
        return this.currentDepth == this.maxDepth;
    }

    @Override
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.put(URL, this.url);
        jsonObject.put(WORD, this.word);
        jsonObject.put(MAX_DEPTH, this.maxDepth);
        jsonObject.put(CURRENT_DEPTH, this.currentDepth);
        return jsonObject;
    }
}
