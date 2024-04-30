package ex2.eventLoop.searcher.filter;

import java.util.List;

public class UrlFilterImpl implements UrlFilter {
    private String urlBody;
    private String word;

    public UrlFilterImpl(final String body, final String word) {
        this.urlBody = body;
        this.word = word;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public String url() {
        return "https://en.wikipedia.org/wiki/Ricci";
    }

    @Override
    public String word() {
        return "ciao";
    }

    @Override
    public int countWord() {
        return 0;
    }

    @Override
    public List<String> findUrls() {
        return List.of();
    }
}
