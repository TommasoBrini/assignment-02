package ex2.core.searcher.concreate;

import ex2.core.dataEvent.DataEvent;
import ex2.core.searcher.BaseSearcher;
import ex2.core.searcher.Searcher;
import ex2.core.searcher.SearcherWorker;
import ex2.server.Server;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SearchLocal extends BaseSearcher implements Searcher {

    public SearchLocal(final SearcherWorker searcherWorker, final DataEvent dataEvent, final String body, final long duration) {
        super(searcherWorker, dataEvent, body, duration);
    }

    @Override
    protected List<String> findUrls() {
        final List<String> findUrls = new ArrayList<>();
        if (this.currentDepth() + 1 <= this.maxDepth()) {
            final Elements links = this.document().select("body a");
            findUrls.addAll(links.stream().map(l -> l.attr("href"))
                    .filter(l -> !l.startsWith("https")
                            && !l.contains("#")
                            && !l.contains("package-summary")
                            && !l.contains(".svg")
                            && !l.contains("Context"))
                    .map(url -> Server.LOCAL_PATH + url)
                    .toList());
        }
        return findUrls;
    }
}
