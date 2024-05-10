package ex2.core.event.factory;

import ex2.core.component.SearchLogic;
import ex2.core.component.Searcher;
import ex2.core.component.concrete.SearcherImpl;
import ex2.core.event.SearchData;
import ex2.worker.LogicWorker;
import io.vertx.core.json.JsonObject;

import static ex2.core.event.SearchUtils.*;

public final class SearchDataFactory {

    public static SearchData create(final LogicWorker.Type workerStrategy, final SearchLogic.Type searcherType, final String url, final String word, final int countWord, final int maxDepth, final long duration) {
        return new SearchDataImpl(workerStrategy, searcherType, url, word, countWord, maxDepth, duration);
    }

    public static SearchData create(final JsonObject jsonObject) {
        return new SearchDataImpl(jsonObject);
    }


    private record SearchDataImpl(LogicWorker.Type workerStrategy, SearchLogic.Type searcherType,
                                  String url, String word, int countWord,  int maxDepth, long duration) implements SearchData {

        public SearchDataImpl(final JsonObject jsonObject) {
            this(LogicWorker.Type.valueOf(jsonObject.getString(WORKER_STRATEGY)),
                    SearchLogic.Type.valueOf(jsonObject.getString(SEARCHER_TYPE)),
                    jsonObject.getString(URL),
                    jsonObject.getString(WORD),
                    jsonObject.getInteger(COUNT_WORD),
                    jsonObject.getInteger(MAX_DEPTH),
                    jsonObject.getLong(DURATION));
        }

        @Override
        public JsonObject toJson() {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.put(WORKER_STRATEGY, this.workerStrategy().name());
            jsonObject.put(SEARCHER_TYPE, this.searcherType().name());
            jsonObject.put(URL, this.url());
            jsonObject.put(WORD, this.word());
            jsonObject.put(COUNT_WORD, this.countWord());
            jsonObject.put(MAX_DEPTH, this.maxDepth());
            jsonObject.put(DURATION, this.duration());
            return jsonObject;
        }
    }
}


