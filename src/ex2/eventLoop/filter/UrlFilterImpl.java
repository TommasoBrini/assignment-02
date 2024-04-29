package ex2.eventLoop.filter;

import java.util.List;

public class UrlFilterImpl implements UrlFilter {
    private String urlBody;

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
