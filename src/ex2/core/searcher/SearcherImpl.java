package ex2.core.searcher;

import ex2.core.CounterFinish;
import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearcherImpl implements Searcher {
    private final SearcherWorker eventLoop;
    private final Document document;
    private final DataEvent data;
    private final CounterFinish counterFinish;
    private final long duration;

    public SearcherImpl(final SearcherWorker searcherWorker, final DataEvent dataEvent, final String body, final CounterFinish counterFinish, final long duration) {
        this.eventLoop = searcherWorker;
        this.data = dataEvent;
        this.document = Jsoup.parse(body);
        this.counterFinish = counterFinish;
        this.duration = duration;
    }

    @Override
    public int currentDepth() {
        return this.data.currentDepth();
    }

    @Override
    public int maxDepth() {
        return this.data.maxDepth();
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
        final Elements texts = this.document.select("body");
        //System.out.println(texts.stream().map(Element::text).toList());
        return (int) texts.stream().map(Element::text).flatMap(text -> Arrays.stream(text.split("\\s+"))).filter(word -> word.equals(this.word())).count();
    }

    private List<String> findUrls() {
        final List<String> findUrls = new ArrayList<>();
        if (this.currentDepth() + 1 <= this.maxDepth()) {
            final Elements links = this.document.select("body a");
            findUrls.addAll(links.stream().map(l -> l.attr("href")).filter(l -> l.startsWith("https")).toList());
        }
        return findUrls;
    }

    @Override
    public int countUrl() {
        return this.findUrls().size();
    }

    @Override
    public boolean isOverMaxDepth() {
        return this.currentDepth() > this.maxDepth();
    }

    @Override
    public void addSearchFindUrls() {
        this.counterFinish.increaseSendIfMaxDepth(this);
        this.findUrls().forEach(url -> {
            final DataEvent dt = new DataEventImpl(url, this.word(), this.data.maxDepth(), this.currentDepth() + 1, this.duration);
            this.eventLoop.addEventUrl(dt);
        });
    }

    @Override
    public String toString() {
        return "CurrentDepth[" + this.currentDepth() + "] maxDepth[" + this.maxDepth() + "]";
    }
}
