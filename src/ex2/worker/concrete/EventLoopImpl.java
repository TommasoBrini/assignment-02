package ex2.worker.concrete;

import ex2.core.event.SearchResponse;
import ex2.core.event.factory.SearchResponseFactory;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class EventLoopImpl extends AbstractWorker {
    private final Vertx vertx;
    private static final String EVENT_URL = "searchUrls";

    public EventLoopImpl() {
        this.vertx = Vertx.vertx();
        this.setupConsumers();
    }

    private void setupConsumers() {
        this.vertx.eventBus().consumer(EVENT_URL, handler -> {
            final JsonObject jsonObject = (JsonObject) handler.body();
            final SearchResponse response = SearchResponseFactory.create(jsonObject);
            final List<SearchResponse> responses = this.searcher().search(response);
            System.out.println("EventLoopImpl.setupConsumers: " + responses.size());
            responses.forEach(this::addEventUrl);
        });
    }

    @Override
    public Type strategy() {
        return Type.EVENT_LOOP;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.onResponseView(response);
        this.vertx.eventBus().send(EVENT_URL, response.toJson());
    }

    @Override
    public void stop() {
        this.vertx.close();
    }



}
