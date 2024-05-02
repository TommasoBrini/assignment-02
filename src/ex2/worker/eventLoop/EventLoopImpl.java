package ex2.worker.eventLoop;

import ex2.core.CounterSearch;
import ex2.core.listener.ModelListener;
import ex2.core.searcher.Searcher;
import ex2.core.searcher.concreate.SearcherWeb;
import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import ex2.core.searcher.SearcherWorker;
import ex2.core.listener.ViewListener;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.uritemplate.UriTemplate;

import java.util.ArrayList;
import java.util.List;

public class EventLoopImpl extends AbstractVerticle implements EventLoop, SearcherWorker {
    private static final String EVENT_URL = "searchUrls";
    private final List<ViewListener> viewListeners;
    private final List<ModelListener> modelListeners;
    private final CounterSearch counterSearch;

    public EventLoopImpl() {
        this.init(Vertx.vertx(), null);
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
        this.counterSearch = new CounterSearch();
        this.setupConsumers();
    }

    private void setupConsumers() {
        this.vertx.eventBus().consumer(EVENT_URL, handler -> {
            final JsonObject jsonObject = (JsonObject) handler.body();
            final DataEvent dataEvent = new DataEventImpl(jsonObject);
            this.searchUrl(dataEvent);
        });
    }

    @Override
    public void startSearch(final DataEvent dataEvent) {
        this.modelListeners.forEach(listener -> listener.onStart(dataEvent));
        this.counterSearch.reset();
        this.searchUrl(dataEvent);
    }

    @Override
    public void stop() {
        this.vertx.close();
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
    public void addEventUrl(final DataEvent dataEvent) {
        this.vertx.eventBus().send(EVENT_URL, dataEvent.toJson());
    }

    private void searchUrl(final DataEvent dataEvent) {
        final long startTime = System.currentTimeMillis();
        final WebClient webClient = WebClient.create(this.vertx);
        webClient.getAbs(UriTemplate.of(dataEvent.url()))
                .send(handler -> {
                    if (handler.succeeded()) {
                        final long duration = System.currentTimeMillis() - startTime;
                        final Searcher searcher = new SearcherWeb(this, dataEvent, handler.result().bodyAsString(),duration);
                        this.viewListeners.forEach(listener -> listener.onResponse(searcher));
                        this.counterSearch.increaseSendIfMaxDepth(searcher);
                        searcher.addSearchFindUrls();
                        this.counterSearch.increaseConsumeIfMaxDepth(dataEvent);

                        if (this.counterSearch.isEnd()) {
                            this.modelListeners.forEach(ModelListener::onFinish);
                            this.viewListeners.forEach(ViewListener::onFinish);
                        }
                    } else {
                        this.viewListeners.forEach(viewListener -> viewListener.onError(handler.cause().getMessage()));
                    }
                });
    }

}
