package ex2.worker.concrete;

import io.vertx.core.Vertx;

public class EventLoopImpl extends AbstractWorker {
    private final Vertx vertx;
    private static final String EVENT_URL = "searchUrls";

    public EventLoopImpl() {
        this.vertx = Vertx.vertx();
        this.setupConsumers();
    }

    private void setupConsumers() {
        this.vertx.eventBus().consumer(EVENT_URL, handler -> {
            final String url = (String) handler.body();
            this.searcher().search(url);
        });
    }

    @Override
    public Type strategy() {
        return Type.EVENT_LOOP;
    }

    @Override
    public void addEventUrl(final String url) {
        this.vertx.eventBus().send(EVENT_URL, url);
    }

    @Override
    public void stop() {
        this.vertx.close();
    }



}
