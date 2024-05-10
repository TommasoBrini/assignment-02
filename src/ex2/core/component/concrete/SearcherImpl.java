package ex2.core.component.concrete;

import ex2.core.component.SearchLogic;
import ex2.core.component.SearchLogicFactory;
import ex2.core.component.Searcher;
import ex2.core.event.SearchResponse;
import ex2.core.event.SearchResponseFactory;
import ex2.core.listener.ViewListener;
import ex2.web.client.ClientService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearcherImpl implements Searcher {
    private ClientService clientService;
    private SearchLogic.Type searchLogicType;
    private String word;
    private String url;
    private int totalWord;
    private long start;

    public SearcherImpl() {
    }

    private int findWord(final Document document) {
        final Elements texts = document.select("body");
        return (int) texts.stream().map(Element::text)
                .flatMap(text -> Arrays.stream(text.split("\\s+")))
                .filter(word -> word.equals(this.word)).count();
    }

    @Override
    public void addListener(final ViewListener viewListener) {
//        this.viewListeners.add(viewListener);
    }

    @Override
    public void setup(final ClientService clientService, final String url, final SearchLogic.Type searchLogicType, final String word) {
        this.clientService = clientService;
        this.url = url;
        this.searchLogicType = searchLogicType;
        this.word = word;
        this.totalWord = 0;
    }

    @Override
    public List<String> initSearch() {
        this.start = System.currentTimeMillis();
        return this.search(this.url);
    }

    @Override
    public List<String> search(final String url) {
        final Document document = this.clientService.findUrl(url);
        final int countWord = this.findWord(document);
        this.totalWord += countWord;
        return SearchLogicFactory.create(this.searchLogicType).findUrls(document);
    }

    @Override
    public int totalWord() {
        return this.totalWord;
    }

    @Override
    public long computeDuration() {
        return System.currentTimeMillis() - this.start;
    }
}