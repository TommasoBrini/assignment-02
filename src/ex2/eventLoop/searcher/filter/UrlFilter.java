package ex2.eventLoop.searcher.filter;

import java.util.List;

public interface UrlFilter {

    int depth();

    String url();

    String word();

    int countWord();

    List<String> findUrls();
}
