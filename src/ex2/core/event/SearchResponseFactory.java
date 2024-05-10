package ex2.core.event;

import ex2.core.component.SearchLogic;
import ex2.worker.LogicWorker;
import io.vertx.core.json.JsonObject;

public final class SearchResponseFactory {

    public static SearchResponse create(
            final LogicWorker.Type workerStrategy,
            final SearchLogic.Type searchLogicType,
            final String url,
            final String word,
            final int countWord,
            final int currentDepth,
            final int maxDepth) {
        return new SearchResponseImpl(workerStrategy, searchLogicType, url, word, countWord, currentDepth, maxDepth);
    }

    public static SearchResponse create(final SearchEvent searchEvent, final int countWord) {
        return new SearchResponseImpl(
                searchEvent.workerStrategy(),
                searchEvent.searchLogicType(),
                searchEvent.url(),
                searchEvent.word(),
                countWord,
                searchEvent.currentDepth(),
                searchEvent.maxDepth());
    }

    public static SearchResponse createUpdateDepth(final SearchResponse searchEvent, final String url, final int countWord) {
        return new SearchResponseImpl(
                searchEvent.workerStrategy(),
                searchEvent.searchLogicType(),
                url,
                searchEvent.word(),
                countWord,
                searchEvent.currentDepth() + 1,
                searchEvent.maxDepth());
    }

    public static SearchResponse create(final JsonObject jsonObject) {
        return new SearchResponseImpl(jsonObject);
    }

    private record SearchResponseImpl(LogicWorker.Type workerStrategy,
                                      SearchLogic.Type searchLogicType,
                                      String url,
                                      String word,
                                      int countWord,
                                      int currentDepth,
                                      int maxDepth) implements SearchResponse {

        public SearchResponseImpl(final JsonObject jsonObject) {
            this(
                    LogicWorker.Type.valueOf(jsonObject.getString(WORKER_STRATEGY)),
                    SearchLogic.Type.valueOf(jsonObject.getString(SEARCH_LOGIC_TYPE)),
                    jsonObject.getString(URL),
                    jsonObject.getString(WORD),
                    jsonObject.getInteger(COUNT_WORD),
                    jsonObject.getInteger(CURRENT_DEPTH),
                    jsonObject.getInteger(MAX_DEPTH)
            );
        }

        private static final String WORKER_STRATEGY = "workerStrategy";
        private static final String SEARCH_LOGIC_TYPE = "searchLogicType";
        private static final String URL = "url";
        private static final String WORD = "word";
        private static final String COUNT_WORD = "countWord";
        private static final String CURRENT_DEPTH = "currentDepth";
        private static final String MAX_DEPTH = "maxDepth";

        @Override
        public boolean isFinished() {
            return this.currentDepth >= this.maxDepth;
        }

        @Override
        public JsonObject toJson() {
            return new JsonObject()
                    .put(WORKER_STRATEGY, this.workerStrategy.name())
                    .put(SEARCH_LOGIC_TYPE, this.searchLogicType.name())
                    .put(URL, this.url)
                    .put(WORD, this.word)
                    .put(COUNT_WORD, this.countWord)
                    .put(CURRENT_DEPTH, this.currentDepth)
                    .put(MAX_DEPTH, this.maxDepth);
        }

        @Override
        public String toString() {
            return "SearchResponseImpl{" +
                    "workerStrategy=" + workerStrategy +
                    ", searchLogicType=" + searchLogicType +
                    ", url='" + url + '\'' +
                    ", word='" + word + '\'' +
                    ", countWord=" + countWord +
                    ", currentDepth=" + currentDepth +
                    ", maxDepth=" + maxDepth +
                    '}';
        }
    }
}
