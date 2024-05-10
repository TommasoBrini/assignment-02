package ex2.core.event;

public interface SearchResponse {
    String url();
    String word();
    int countWord();
    int currentDepth();
}
