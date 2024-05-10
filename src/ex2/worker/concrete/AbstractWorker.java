package ex2.worker.concrete;

import ex2.core.component.Searcher;
import ex2.core.event.SearchResponse;
import ex2.core.event.SearchResponseFactory;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.worker.LogicWorker;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorker implements LogicWorker {
    private final List<ViewListener> viewListeners;
    private final List<ModelListener> modelListeners;
    private Searcher searcher;

    public AbstractWorker() {
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
    }

    protected Searcher searcher() {
        return this.searcher;
    }

    @Override
    public void start(final Searcher searcher) {
        this.searcher = searcher;
        final List<String> urls = this.searcher.initSearch();
        this.modelListeners.forEach(listener -> listener.onStart(null));
//        this.viewListeners.forEach(listener -> listener.onStart());
        urls.forEach(this::addEventUrl);
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.modelListeners.add(modelListener);
    }

    public abstract void addEventUrl(final String url);
}
