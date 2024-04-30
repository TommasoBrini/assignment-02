package ex2.eventLoop.searcher;

import ex2.eventLoop.core.WorkerLoop;

import java.util.ArrayList;
import java.util.List;


public class SearcherImpl implements Searcher {
    private final WorkerLoop eventLoop;
    private final String urlBody;
    private final String url;
    private final String word;
    private final int depth;

    public SearcherImpl(final WorkerLoop workerLoop, final String url, final String body, final String word, final int depth) {
        this.eventLoop = workerLoop;
        this.url = url;
        this.urlBody = body;
        this.word = word;
        this.depth = depth;
    }

    @Override
    public int depth() {
        return this.depth;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public String word() {
        return this.word;
    }

    @Override
    public int countWord() {
        // TODO: ricerca parola
        return 0;
    }

    @Override
    public int findUrls() {
        final List<String> findUrls = new ArrayList<>();
        // TODO: ricerca url

        findUrls.forEach(url -> this.eventLoop.searchUrl(url, this.word, this.depth - 1));
        return findUrls.size();
    }
}
