package ex2.core.component.searcher.factory;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.FactorySearcher;
import ex2.core.component.searcher.SearcherType;
import ex2.core.component.searcher.SearcherWorker;
import ex2.core.component.searcher.concrete.SearcherLocal;
import ex2.core.component.searcher.concrete.SearcherWeb;
import org.jsoup.nodes.Document;

public class SimpleFactory implements FactorySearcher {

    @Override
    public Searcher createLocal(final SearcherWorker searcherWorker,
                                final DataEvent dataEvent,
                                final Document document, final long duration) {
        return new SearcherLocal(searcherWorker, dataEvent, document, duration);
    }

    @Override
    public Searcher createWeb(final SearcherWorker searcherWorker,
                              final DataEvent dataEvent,
                              final Document document, final long duration) {
        return new SearcherWeb(searcherWorker, dataEvent, document, duration);
    }

    @Override
    public Searcher create(final SearcherType id,
                           final SearcherWorker searcherWorker,
                           final DataEvent dataEvent,
                           final Document document,
                           final long duration) {
        return switch (id) {
            case LOCAL -> this.createLocal(searcherWorker, dataEvent, document, duration);
            case WEB -> this.createWeb(searcherWorker, dataEvent, document, duration);
        };
    }

}
