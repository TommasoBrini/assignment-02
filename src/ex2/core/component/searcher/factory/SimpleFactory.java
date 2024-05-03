package ex2.core.component.searcher.factory;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.FactorySearcher;
import ex2.core.component.searcher.SearcherType;
import ex2.core.component.searcher.SearcherWorker;
import ex2.core.component.searcher.concreate.SearchLocal;
import ex2.core.component.searcher.concreate.SearcherWeb;

public class SimpleFactory implements FactorySearcher {

    @Override
    public Searcher createLocal(final SearcherWorker searcherWorker,
                                final DataEvent dataEvent,
                                final String body,
                                final long duration) {
        return new SearchLocal(searcherWorker, dataEvent, body, duration);
    }

    @Override
    public Searcher createWeb(final SearcherWorker searcherWorker,
                              final DataEvent dataEvent,
                              final String body,
                              final long duration) {
        return new SearcherWeb(searcherWorker, dataEvent, body, duration);
    }

    @Override
    public Searcher create(final SearcherType id,
                           final SearcherWorker searcherWorker,
                           final DataEvent dataEvent,
                           final String body,
                           final long duration) {
        return switch (id) {
            case LOCAL -> this.createLocal(searcherWorker, dataEvent, body, duration);
            case WEB -> this.createWeb(searcherWorker, dataEvent, body, duration);
        };
    }

}
