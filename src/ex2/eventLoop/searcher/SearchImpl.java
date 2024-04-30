package ex2.eventLoop.searcher;

import ex2.eventLoop.searcher.filter.UrlFilter;
import ex2.eventLoop.searcher.filter.UrlFilterImpl;

public class SearchImpl implements Searcher {
    private final UrlFilter urlFilter;

    public SearchImpl(final String body) {
        this.urlFilter = new UrlFilterImpl(body, "");
    }

    @Override
    public void search(final String url, final String word, final int depth) {

    }
}
