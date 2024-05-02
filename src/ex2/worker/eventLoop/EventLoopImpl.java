package ex2.worker.eventLoop;

import ex2.core.CounterFinish;
import ex2.core.searcher.Searcher;
import ex2.core.searcher.SearcherImpl;
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
    private final CounterFinish counterFinish;

    public EventLoopImpl() {
        this.init(Vertx.vertx(), null);
        this.viewListeners = new ArrayList<>();
        this.counterFinish = new CounterFinish();
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
    public void stop() {
        this.vertx.close();
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.vertx.eventBus().send(EVENT_URL, dataEvent.toJson());
    }

    @Override
    public void searchUrl(final DataEvent dataEvent) {
        if (dataEvent.isOverMaxDepth()) {
            return;
        }
        final long startTime = System.currentTimeMillis();
        final WebClient webClient = WebClient.create(this.vertx);
        webClient.getAbs(UriTemplate.of(dataEvent.url()))
                .send(handler -> {
                    if (handler.succeeded()) {
                        // Gestione della risposta ricevuta
                        final long duration = System.currentTimeMillis() - startTime;
                        System.out.println("Response status code: " + handler.result().statusCode());
                        System.out.println("Response body:");
                        System.out.println(dataEvent.url());

                        final Searcher searcher = new SearcherImpl(this, dataEvent, handler.result().bodyAsString(), this.counterFinish, duration);
                        this.viewListeners.forEach(listener -> listener.onResponse(searcher));
                        searcher.addSearchFindUrls();
                        this.counterFinish.increaseConsumeIfMaxDepth(dataEvent);
                        if (this.counterFinish.isEnd()) {
                            System.out.println("FINISH SEARCH");
                        }
                    } else {
                        // Gestione degli errori
                        System.err.println("Request failed: " + handler.cause().getMessage());
                        this.viewListeners.forEach(viewListener -> viewListener.onError(handler.cause().getMessage()));
                    }
                });
    }

}
