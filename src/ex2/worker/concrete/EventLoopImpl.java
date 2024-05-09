package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.component.searcher.Searcher;
import ex2.web.client.ClientService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class EventLoopImpl extends AbstractWorker {
    private Searcher searcher;
    private final Vertx vertx;
    private static final String EVENT_URL = "searchUrls";

    public EventLoopImpl() {
        this.vertx = Vertx.vertx();
        this.setupConsumers();
    }

    private void setupConsumers() {
        this.vertx.eventBus().consumer(EVENT_URL, handler -> {
            final JsonObject jsonObject = (JsonObject) handler.body();
            final DataEvent dataEvent = new DataEventImpl(jsonObject);
            this.searcher.search(__)
        });
    }

    @Override
    public void start(final Searcher searcher) {
        this.searcher = searcher;
        final List<String> urls = this.searcher.initSearch();
        urls.forEach(url -> this.addEventUrl(____));
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.vertx.eventBus().send(EVENT_URL, dataEvent.toJson());
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.EVENT_LOOP;
    }

    @Override
    public void stop() {
        this.vertx.close();
    }



}
