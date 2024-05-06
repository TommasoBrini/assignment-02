package ex2.core.component.searcher.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherType;
import ex2.core.component.searcher.SearcherWorker;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SearcherWeb extends BaseSearcher implements Searcher {

    public SearcherWeb(final SearcherWorker searcherWorker, final DataEvent dataEvent, final String body, final long duration) {
        super(searcherWorker, dataEvent, body, duration);
    }

    @Override
    protected List<String> getUrls() {
        final List<String> findUrls = new ArrayList<>();
        if (this.currentDepth() + 1 <= this.maxDepth()) {
            final Elements links = this.document().select("body a");
            findUrls.addAll(links.stream().map(l -> l.attr("href"))
                    .filter(l -> l.startsWith("http") && this.checkExtensions(l))
                    .toList());
        }
        return findUrls;
    }


    @Override
    public SearcherType searcherType() {
        return SearcherType.WEB;
    }
}
