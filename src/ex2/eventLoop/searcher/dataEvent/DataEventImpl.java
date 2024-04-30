package ex2.eventLoop.searcher.dataEvent;

public record DataEventImpl(String url, String word, int maxDepth, int currentDepth) implements DataEvent {

    public boolean isMaxDepth() {
        return this.currentDepth == this.maxDepth;
    }
}
