package ex2.eventLoop.searcher;

public interface Searcher {

    int depth();

    String url();

    String word();

    int countWord();

    int findUrls();
}
