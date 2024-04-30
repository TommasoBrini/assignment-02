package ex2.eventLoop.searcher.dataEvent;

public record DataEventImpl(String url, String word, int depth) implements DataEvent {

}
