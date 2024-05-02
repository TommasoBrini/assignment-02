package ex2.core.searcher;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class SearcherImpl implements Searcher {
    private final SearcherWorker eventLoop;
    private final Document document;
    private final DataEvent data;
    private final long duration;

    public SearcherImpl(final SearcherWorker searcherWorker, final DataEvent dataEvent, final String body, final long duration) {
        this.eventLoop = searcherWorker;
        this.data = dataEvent;
        this.document = Jsoup.parse(body);
        this.duration = duration;
    }

    @Override
    public int currentDepth() {
        return this.data.currentDepth();
    }

    @Override
    public String url() {
        return this.data.url();
    }

    @Override
    public String word() {
        return this.data.word();
    }

    @Override
    public long duration() {
        return this.duration;
    }

    @Override
    public int countWord() {
        Elements texts = document.select("p");
        System.out.println(texts.stream().map(Element::text).toList());
        return (int) texts.stream().map(Element::text).filter(s -> s.contains(this.word())).count();
    }

    @Override
    public int findUrls() {
        Elements links = document.select("body a");
        final List<String> findUrls = links.stream().map(l -> l.attr("href")).filter(l -> l.startsWith("https")).toList();
        for (String link : findUrls){
            DataEvent dt = new DataEventImpl(link, this.word(), this.data.maxDepth(), this.currentDepth() + 1, this.duration);
            this.eventLoop.addEventUrl(dt);
        }
        return findUrls.size();
    }
}
