package ex2.core.component.searcher;

public interface Searcher {

    int currentDepth();

    int maxDepth();

    String url();

    String word();

    long duration();

    int countWord();

    int countUrl();

    void addSearchFindUrls();

    SearcherType searcherType();
}
