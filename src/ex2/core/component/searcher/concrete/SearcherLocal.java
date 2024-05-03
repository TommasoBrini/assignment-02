package ex2.core.component.searcher.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherWorker;
import ex2.server.Server;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SearcherLocal extends BaseSearcher implements Searcher {

    public SearcherLocal(final SearcherWorker searcherWorker, final DataEvent dataEvent, final String body, final long duration) {
        super(searcherWorker, dataEvent, body, duration);
    }

    @Override
    protected List<String> getUrls() {
        final List<String> urls = new ArrayList<>();
        if (this.currentDepth() + 1 <= this.maxDepth()) {
            final Elements links = this.document().select("body a");
            urls.addAll(links.stream().map(l -> l.attr("href"))
                    .filter(l -> !l.startsWith("http")
                            && !l.contains("#")
                            && !l.contains("package-summary")
                            && !l.contains(".svg")
                            && !l.contains("Context"))
                    .map(url -> Server.LOCAL_PATH + url)
                    .toList());
        }
        return urls;
    }

}
