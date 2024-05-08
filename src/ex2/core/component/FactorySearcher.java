package ex2.core.component;

import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherType;
import ex2.core.component.searcher.SearcherWorker;
import org.jsoup.nodes.Document;

public interface FactorySearcher {

    Searcher createLocal(final SearcherWorker searcherWorker,
                         final DataEvent dataEvent,
                         final Document document,
                         final long duration);

    Searcher createWeb(final SearcherWorker searcherWorker,
                       final DataEvent dataEvent,
                       final Document document,
                       final long duration);

    Searcher create(final SearcherType id,
                    final SearcherWorker searcherWorker,
                    final DataEvent dataEvent,
                    final Document document,
                    final long duration);
}
