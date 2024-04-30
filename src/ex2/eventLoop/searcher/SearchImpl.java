package ex2.eventLoop.searcher;

import ex2.eventLoop.filter.UrlFilter;

public class SearchImpl implements Searcher {
    private final UrlFilter urlFilter;

    public SearchImpl(final UrlFilter urlFilter) {
        this.urlFilter = urlFilter;
    }

    @Override
    public void search(final String url, final String word, final int depth) {

    }
}
