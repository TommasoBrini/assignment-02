package ex2.worker.concrete;

import ex2.core.component.CounterSearch;
import ex2.core.component.DataEvent;
import ex2.core.component.FactorySearcher;
import ex2.core.component.concrete.CounterSearchImpl;
import ex2.core.component.searcher.SearcherType;
import ex2.core.component.searcher.SearcherWorker;
import ex2.core.component.searcher.factory.SimpleFactory;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.worker.LogicWorker;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorker implements LogicWorker, SearcherWorker {

    protected static final int STATUS_CODE_MIN = 200;
    protected static final int STATUS_CODE_MAX = 300;
    protected final List<ViewListener> viewListeners;
    protected final List<ModelListener> modelListeners;
    protected final CounterSearch counterSearch;
    protected final FactorySearcher searcherFactory;
    protected SearcherType searcherType;

    public AbstractWorker() {
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
        this.counterSearch = new CounterSearchImpl();
        this.searcherFactory = new SimpleFactory();
    }

    @Override
    public void startSearch(final SearcherType searcherType, final DataEvent dataEvent) {
        this.modelListeners.forEach(listener -> listener.onStart(dataEvent));
        this.searcherType = searcherType;
        this.counterSearch.reset();
        this.searchUrl(dataEvent);
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.modelListeners.add(modelListener);
    }

    @Override
    public abstract void addEventUrl(final DataEvent dataEvent);

    protected abstract void searchUrl(final DataEvent dataEvent);

}
