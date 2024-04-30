package ex2.eventLoop.searcher.filter;

import java.util.List;

public interface UrlFilter {

    void setBodyUrl(final String bodyUrl);

    int countWord(final String word);

    List<String> findUrls();
}
