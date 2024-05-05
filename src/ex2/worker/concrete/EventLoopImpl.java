package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import io.vertx.core.json.JsonObject;

public class EventLoopImpl extends AbstractWorker {
    private static final String EVENT_URL = "searchUrls";

    public EventLoopImpl() {
        super();
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
    public WorkerStrategy strategy() {
        return WorkerStrategy.EVENT_LOOP;
    }
}
