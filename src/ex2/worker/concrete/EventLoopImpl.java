package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.web.client.ClientService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class EventLoopImpl extends AbstractWorker {
    private final Vertx vertx; // VERTX
    private static final String EVENT_URL = "searchUrls";

    public EventLoopImpl(final ClientService clientService) {
        super(clientService);
        this.vertx = Vertx.vertx();
        this.setupConsumers();
    }

    private void setupConsumers() {
        this.vertx.eventBus().consumer(EVENT_URL, handler -> {
            final JsonObject jsonObject = (JsonObject) handler.body();
            final DataEvent dataEvent = new DataEventImpl(jsonObject);
            this.clientService.onSearch(dataEvent.url());
        });
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
