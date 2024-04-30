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
    public void setBodyUrl(final String bodyUrl) {
        this.urlBody = bodyUrl;
    }

    @Override
    public int countWord(final String word) {
        return 0;
    }

    @Override
    public List<String> findUrls() {
        return List.of();
    }
}
