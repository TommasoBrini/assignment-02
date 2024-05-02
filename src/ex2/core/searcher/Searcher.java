package ex2.core.searcher;

public interface Searcher {

    int currentDepth();

    String url();

    String word();

    long duration();

    int countWord();

    int findUrls();
}
