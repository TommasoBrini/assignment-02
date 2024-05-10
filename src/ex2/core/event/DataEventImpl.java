package ex2.core.event;

import ex2.core.component.SearchLogic;
import ex2.worker.LogicWorker;
import io.vertx.core.json.JsonObject;

public record DataEventImpl(LogicWorker.Type workerStrategy, SearchLogic.Type searcherType, String url, String word,
                            int maxDepth, int currentDepth, long duration) implements DataEvent {

    private static final String WORKER_STRATEGY = "workerStrategy";
    private static final String SEARCHER_TYPE = "searcherType";
    private static final String URL = "url";
    private static final String WORD = "word";
    private static final String MAX_DEPTH = "maxDepth";
    private static final String CURRENT_DEPTH = "currentDepth";
    private static final String DURATION = "duration";

    public DataEventImpl(final LogicWorker.Type workerStrategy, final SearchLogic.Type searcherType, final String url, final String word, final int maxDepth, final int currentDepth) {
        this(workerStrategy, searcherType, url, word, maxDepth, currentDepth, 0L);
    }

    public DataEventImpl(final JsonObject jsonObject) {
        this(LogicWorker.Type.valueOf(jsonObject.getString(WORKER_STRATEGY)),
                SearchLogic.Type.valueOf(jsonObject.getString(SEARCHER_TYPE)),
                jsonObject.getString(URL),
                jsonObject.getString(WORD),
                jsonObject.getInteger(MAX_DEPTH),
                jsonObject.getInteger(CURRENT_DEPTH),
                jsonObject.getLong(DURATION));
    }

    public boolean isOverMaxDepth() {
        return this.currentDepth > this.maxDepth;
    }

    @Override
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.put(WORKER_STRATEGY, this.workerStrategy.name());
        jsonObject.put(SEARCHER_TYPE, this.searcherType.name());
        jsonObject.put(URL, this.url);
        jsonObject.put(WORD, this.word);
        jsonObject.put(MAX_DEPTH, this.maxDepth);
        jsonObject.put(CURRENT_DEPTH, this.currentDepth);
        jsonObject.put(DURATION, this.duration);
        return jsonObject;
    }
}
