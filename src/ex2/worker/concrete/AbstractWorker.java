package ex2.worker.concrete;

import ex2.core.component.Searcher;
import ex2.core.event.SearchResponse;
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
        final SearchResponse response = this.searcher.initSearch();
        this.modelListeners.forEach(listener -> listener.onStart(response));
        this.addEventUrl(response);
    }

    protected void onResponseView(final SearchResponse response) {
        this.viewListeners.forEach(listener -> listener.onResponse(response));
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.modelListeners.add(modelListener);
    }

    public abstract void addEventUrl(final SearchResponse response);

    public void onFinishListener() {
        this.modelListeners.forEach(listener -> listener.onFinish(this.searcher));
        this.viewListeners.forEach(listener -> listener.onFinish(this.searcher.totalWord()));
    }
}
