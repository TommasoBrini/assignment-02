package ex2.core.searcher;

import ex2.core.dataEvent.DataEvent;
import ex2.core.searcher.concreate.SearchLocal;
import ex2.core.searcher.concreate.SearcherWeb;

public class SearcherFactory {
    public Searcher create(final SearcherType id,
                           final SearcherWorker searcherWorker,
                           final DataEvent dataEvent,
                           final String body,
                           final long duration)  {
        return switch (id) {
            case LOCAL -> new SearchLocal(searcherWorker, dataEvent, body, duration);
            case WEB -> new SearcherWeb(searcherWorker, dataEvent, body, duration);
        };
    }

}
