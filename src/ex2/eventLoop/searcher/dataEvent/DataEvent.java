package ex2.eventLoop.searcher.dataEvent;

public interface DataEvent {

    String url();

    String word();

    int currentDepth();

    int maxDepth();
}
