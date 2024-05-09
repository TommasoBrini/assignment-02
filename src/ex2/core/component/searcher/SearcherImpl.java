package ex2.core.component.searcher;

import ex2.web.client.ClientService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

public class SearcherImpl implements Searcher {
    private ClientService clientService;
    private SearchLogic.Type searchLogicType;
    private String url;
    private String word;
    private int totalWord;
    private long start;

    private int findWord(final Document document) {
        final Elements texts = document.select("body");
        return (int) texts.stream().map(Element::text)
                .flatMap(text -> Arrays.stream(text.split("\\s+")))
                .filter(word -> word.equals(this.word)).count();
    }

    @Override
    public void setup(final ClientService clientService, final SearchLogic.Type searchLogicType, final String url, final String word) {
        this.clientService = clientService;
        this.searchLogicType = searchLogicType;
        this.url = url;
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
        this.totalWord += this.findWord(document);
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