package ex2.core.event;

public final class SearchResponseFactory {

    public static SearchResponse create(final String url, final String word, final int countWord, final int currentDepth) {
        return new SearchResponseImpl(url, word, countWord, currentDepth);
    }

    private record SearchResponseImpl(String url, String word, int countWord, int currentDepth) implements SearchResponse {
    }
}
