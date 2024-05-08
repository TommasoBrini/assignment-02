package ex2.worker.concrete;

import ex2.core.component.CounterSearch;
import ex2.core.component.DataEvent;
import ex2.core.component.FactorySearcher;
import ex2.core.component.concrete.CounterSearchImpl;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherType;
import ex2.core.component.searcher.SearcherWorker;
import ex2.core.component.searcher.factory.SimpleFactory;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.server.client.ClientService;
import ex2.worker.LogicWorker;
import org.jsoup.nodes.Document;

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
    protected ClientService clientService;

    public AbstractWorker(final ClientService clientService) {
        this.clientService = clientService;
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
        this.counterSearch = new CounterSearchImpl();
        this.searcherFactory = new SimpleFactory();
    }

    @Override
    public void start(final DataEvent dataEvent) {
        this.modelListeners.forEach(listener -> listener.onStart(dataEvent));
        this.searcherType = dataEvent.searcherType();
        this.counterSearch.reset();
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

    @Override
    public void onResponse(final DataEvent dataEvent, final Document doc, final long startTime) {
        final long duration = System.currentTimeMillis() - startTime;
        final Searcher searcher = this.searcherFactory.create(this.searcherType, this, dataEvent, doc, duration);
        this.viewListeners.forEach(listener -> listener.onResponse(searcher));
        this.counterSearch.increaseSendIfMaxDepth(searcher);
        searcher.addSearchFindUrls();
        this.counterSearch.increaseConsumeIfMaxDepth(dataEvent);

        if (this.counterSearch.isEnd()) {
            this.modelListeners.forEach(ModelListener::onFinish);
            this.viewListeners.forEach(ViewListener::onFinish);
        }
    }

}
