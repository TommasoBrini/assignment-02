package ex2.eventLoop.searcher;

import ex2.eventLoop.core.WorkerLoop;
import ex2.eventLoop.searcher.dataEvent.DataEvent;
import ex2.eventLoop.searcher.dataEvent.DataEventImpl;

import java.util.ArrayList;
import java.util.List;


public class SearcherImpl implements Searcher {
    private final WorkerLoop eventLoop;
    private final String urlBody;
    private final DataEvent data;

    public SearcherImpl(final WorkerLoop workerLoop, final DataEvent dataEvent, final String body) {
        this.eventLoop = workerLoop;
        this.data = dataEvent;
        this.urlBody = body;
    }

    @Override
    public int currentDepth() {
        return this.data.currentDepth();
    }

    @Override
    public String url() {
        return this.data.url();
    }

    @Override
    public String word() {
        return this.data.word();
    }

    @Override
    public int countWord() {
        // TODO: ricerca parola
        return 0;
    }

    @Override
    public int findUrls() {
        final int counter = 0;
        final List<String> findUrls = new ArrayList<>();
        // TODO: ricerca url

//        this.eventLoop.addEventUrl(new DataEventImpl(___, this.word(), this.currentDepth() - 1));

//        findUrls.forEach(url -> this.eventLoop.searchUrl(url, this.word, this.currentDepth - 1));
        return findUrls.size();
    }
}
