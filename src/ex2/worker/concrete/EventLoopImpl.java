package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.component.searcher.Searcher;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class EventLoopImpl extends AbstractWorker implements Verticle {
    private Vertx vertx; // VERTX
    private Context context; // VERTX

    private final WebClient webClient;
    private static final String EVENT_URL = "searchUrls";

    public EventLoopImpl() {
        super();
        this.init(Vertx.vertx(), null);
        this.webClient = WebClient.create(this.vertx);
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
    public void addEventUrl(final DataEvent dataEvent) {
        this.vertx.eventBus().send(EVENT_URL, dataEvent.toJson());
    }

    @Override
    protected void searchUrl(final DataEvent dataEvent) {
        final long startTime = System.currentTimeMillis();
        this.webClient.getAbs(dataEvent.url())
                .send(handler -> {
                    if (handler.succeeded()) {
                        final long duration = System.currentTimeMillis() - startTime;
                        final int statusCode = handler.result().statusCode();
                        if (statusCode >= STATUS_CODE_MIN && statusCode < STATUS_CODE_MAX && handler.result().bodyAsString() != null) {
                            System.out.println(handler.result().statusMessage() + " " + dataEvent.url());
                            final Searcher searcher = this.searcherFactory.create(this.searcherType, this, dataEvent, handler.result().bodyAsString(), duration);
                            this.viewListeners.forEach(listener -> listener.onResponse(searcher));
                            this.counterSearch.increaseSendIfMaxDepth(searcher);
                            searcher.addSearchFindUrls();
                        }
                        this.counterSearch.increaseConsumeIfMaxDepth(dataEvent);

                        if (this.counterSearch.isEnd()) {
                            this.modelListeners.forEach(ModelListener::onFinish);
                            this.viewListeners.forEach(ViewListener::onFinish);
                        }
                    } else {
                        System.out.println("ERROR -> " + handler.cause().getMessage());
                        this.viewListeners.forEach(viewListener -> viewListener.onError(handler.cause().getMessage()));
                    }
                });
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.EVENT_LOOP;
    }

    @Override
    public Vertx getVertx() {
        return this.vertx;
    }

    @Override
    public void init(final Vertx vertx, final Context context) {
        this.vertx = vertx;
        this.context = context;
    }

    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        startPromise.complete();
    }

    @Override
    public void stop(final Promise<Void> stopPromise) throws Exception {
        stopPromise.complete();
    }

    @Override
    public void stop() {
        this.webClient.close();
        this.vertx.close();
    }
}
