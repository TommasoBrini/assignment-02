package ex2.core.component.searcher.concreate;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherWorker;
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
