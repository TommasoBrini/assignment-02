package ex2.core;

import ex2.web.client.ClientService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

public class SearcherImpl implements Searcher {
    private final String word;
    private int totalWord;
    private long start;

    public SearcherImpl(final String word) {
        this.word = word;
        this.totalWord = 0;
    }

    private int findWord(final Document document) {
        final Elements texts = document.select("body");
        return (int) texts.stream().map(Element::text)
                .flatMap(text -> Arrays.stream(text.split("\\s+")))
                .filter(word -> word.equals(this.word)).count();
    }

    @Override
    public List<String> initSearch(final ClientService clientService, final SearchLogic.Type searchLogicType, final String url) {
        this.start = System.currentTimeMillis();
        return this.search(clientService, searchLogicType, url);
    }

    @Override
    public List<String> search(final ClientService clientService, final SearchLogic.Type searchLogicType, final String url) {
        final Document document = clientService.findUrl(url);
        this.totalWord += this.findWord(document);
        return SearchLogicFactory.create(searchLogicType).findUrls(document);
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