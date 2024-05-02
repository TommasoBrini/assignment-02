package ex2.core.searcher;

public interface Searcher {

    int currentDepth();

    int maxDepth();

    String url();

    String word();

    long duration();

    int countWord();

    int countUrl();

    void addSearchFindUrls();
}
