package ex2.core.searcher;

public interface Searcher {

    int currentDepth();

    String url();

    String word();

    int countWord();

    int findUrls();
}
