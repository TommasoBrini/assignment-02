package ex2.core.component.searcher.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.component.searcher.SearcherWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseSearcher {
    private final SearcherWorker searcherWorker;
    private final Document document;
    private final DataEvent data;
    private final long duration;
    private final Supplier<List<String>> urlsLazy;
    protected final List<String> extension = Arrays.asList(".svg", ".png", ".jpg", ".jpeg", ".gif", ".pdf", ".mp4", "mp3", ".mp3", ".avi", ".flv", ".mov", ".wmv", ".zip", ".dmg", ".exe", ".msi");

    public BaseSearcher(final SearcherWorker searcherWorker, final DataEvent dataEvent, final String body, final long duration) {
        this.searcherWorker = searcherWorker;
        this.data = dataEvent;
        this.document = Jsoup.parse(body);
        this.duration = duration;
        this.urlsLazy = new Supplier<>() {
            volatile List<String> value;

            @Override
            public List<String> get() {
                if (this.value == null) {
                    synchronized (this) {
                        if (this.value == null) {
                            this.value = BaseSearcher.this.getUrls(); // Calcola il valore lazy
                        }
                    }
                }
                return this.value;
            }
        };
    }

    protected boolean checkExtensions(final String l) {
        return this.extension.stream().noneMatch(l::endsWith);
    }

    protected abstract List<String> getUrls();

    protected List<String> findUrls() {
        return this.urlsLazy.get();
    }

    protected Document document() {
        return this.document;
    }

    protected SearcherWorker searcherWorker() {
        return this.searcherWorker;
    }

    public int currentDepth() {
        return this.data.currentDepth();
    }

    public int maxDepth() {
        return this.data.maxDepth();
    }

    public String url() {
        return this.data.url();
    }

    public String word() {
        return this.data.word();
    }

    public long duration() {
        return this.duration;
    }

    public int countUrl() {
        return this.findUrls().size();
    }

    public int countWord() {
        final Elements texts = this.document().select("body");
        return (int) texts.stream().map(Element::text).flatMap(text -> Arrays.stream(text.split("\\s+"))).filter(word -> word.equals(this.word())).count();
    }

    public void addSearchFindUrls() {
        this.findUrls().forEach(url -> {
            final DataEvent dt = new DataEventImpl(this.data.workerStrategy(), this.data.searcherType(), url, this.word(), this.maxDepth(), this.currentDepth() + 1, this.duration());
            this.searcherWorker().addEventUrl(dt);
        });
    }

    @Override
    public String toString() {
        return "Url->" + this.url() + " CurrentDepth[" + this.currentDepth() + "] maxDepth[" + this.maxDepth() + "]";
    }
}
