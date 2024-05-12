package ex2.core.event.factory;

import ex2.core.component.SearchLogic;
import ex2.core.event.SearchEvent;
import ex2.core.event.SearchResponse;
import ex2.worker.LogicWorker;
import io.vertx.core.json.JsonObject;

import static ex2.core.event.SearchUtils.*;

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
                    SearchLogic.Type.valueOf(jsonObject.getString(SEARCHER_TYPE)),
                    jsonObject.getString(URL),
                    jsonObject.getString(WORD),
                    jsonObject.getInteger(COUNT_WORD),
                    jsonObject.getInteger(CURRENT_DEPTH),
                    jsonObject.getInteger(MAX_DEPTH)
            );
        }

        @Override
        public boolean isFinished() {
            return this.currentDepth >= this.maxDepth;
        }

        @Override
        public JsonObject toJson() {
            return new JsonObject()
                    .put(WORKER_STRATEGY, this.workerStrategy.name())
                    .put(SEARCHER_TYPE, this.searchLogicType.name())
                    .put(URL, this.url)
                    .put(WORD, this.word)
                    .put(COUNT_WORD, this.countWord)
                    .put(CURRENT_DEPTH, this.currentDepth)
                    .put(MAX_DEPTH, this.maxDepth);
        }

        @Override
        public String toString() {
            return "SearchResponseImpl{" +
                    "workerStrategy=" + this.workerStrategy +
                    ", searchLogicType=" + this.searchLogicType +
                    ", url='" + this.url + '\'' +
                    ", word='" + this.word + '\'' +
                    ", countWord=" + this.countWord +
                    ", currentDepth=" + this.currentDepth +
                    ", maxDepth=" + this.maxDepth +
                    '}';
        }
    }
}
