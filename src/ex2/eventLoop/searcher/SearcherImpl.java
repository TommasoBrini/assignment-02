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

    public SearcherImpl(final WorkerLoop workerLoop, final String url, final String body, final String word, final int depth) {
        this.eventLoop = workerLoop;
        this.data = new DataEventImpl(url, word, depth);
        this.urlBody = body;
    }

    @Override
    public int depth() {
        return this.data.depth();
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

//        this.eventLoop.addEventUrl(new DataEventImpl(___, this.word(), this.depth() - 1));

//        findUrls.forEach(url -> this.eventLoop.searchUrl(url, this.word, this.depth - 1));
        return findUrls.size();
    }
}
