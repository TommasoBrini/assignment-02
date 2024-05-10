package ex2.core.component.concrete;

import ex2.core.component.SearchLogicFactory;
import ex2.core.component.Searcher;
import ex2.core.event.SearchData;
import ex2.core.event.SearchEvent;
import ex2.core.event.SearchResponse;
import ex2.core.event.factory.SearchDataFactory;
import ex2.core.event.factory.SearchResponseFactory;
import ex2.web.client.ClientService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SearcherImpl implements Searcher {
    private final Set<String> cache;
    private final AtomicInteger totalWord = new AtomicInteger();

    private ClientService clientService;
    private SearchEvent searchEvent;
    private long start;

    public SearcherImpl() {
        this.cache = new HashSet<>();
    }

    private int findWord(final Document document) {
        final Elements texts = document.select("body");
        return (int) texts.stream().map(Element::text)
                .flatMap(text -> Arrays.stream(text.split("\\s+")))
                .filter(word -> word.equals(this.searchEvent.word())).count();
    }

    private Document findDocument(final String url) {
        return this.clientService.findDocument(url);
    }

    @Override
    public void setup(final ClientService clientService, final SearchEvent searchEvent) {
        this.clientService = clientService;
        this.searchEvent = searchEvent;
        this.totalWord.set(0);
    }

    @Override
    public SearchResponse initSearch() {
        this.start = System.currentTimeMillis();
        final Document document = this.findDocument(this.searchEvent.url());
        final SearchResponse response = SearchResponseFactory.create(this.searchEvent, this.findWord(document));
        this.cache.add(response.url());
        return response;
    }

    @Override
    public List<SearchResponse> search(final SearchResponse parent) {
        if (parent.isFinished()) return List.of();

        final List<String> childrenUrls = SearchLogicFactory.create(parent.searchLogicType())
                .findUrls(this.findDocument(parent.url()))
                .stream()
                .filter(url -> !this.cache.contains(url))
                .toList();

        final List<SearchResponse> searchResponses = childrenUrls
                .stream()
                .map(url -> SearchResponseFactory.createUpdateDepth(parent, url, this.findWord(this.findDocument(url))))
                .toList();

        final List<Integer> countWords = searchResponses.stream().map(SearchResponse::countWord).toList();
        this.totalWord.set(this.totalWord() + countWords.stream().reduce(0, Integer::sum));
        return searchResponses;
    }


    @Override
    public int totalWord() {
        return this.totalWord.get();
    }

    private long computeDuration() {
        return System.currentTimeMillis() - this.start;
    }

    @Override
    public SearchData dataOnFinish() {
        return SearchDataFactory.create(
                this.searchEvent.workerStrategy(), this.searchEvent.searchLogicType(),
                this.searchEvent.url(), this.searchEvent.word(), this.totalWord.get(),
                this.searchEvent.maxDepth(), this.computeDuration());
    }
}