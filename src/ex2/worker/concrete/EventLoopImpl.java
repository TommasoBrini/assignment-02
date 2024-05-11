package ex2.worker.concrete;

import ex2.core.event.SearchResponse;
import ex2.core.event.factory.SearchResponseFactory;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class EventLoopImpl extends AbstractWorker {
    private final Vertx vertx;

    public EventLoopImpl() {
        this.vertx = Vertx.vertx();
    }

    @Override
    public Type strategy() {
        return Type.EVENT_LOOP;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.onResponseView(response);

        final Future<Void> promise = this.vertx.executeBlocking(() -> {
            final List<SearchResponse> responses = this.searcher().search(response);
            responses.forEach(this::onResponseView);

            final List<CompletableFuture<Void>> nestedFutures = responses.stream()
                .flatMap(res -> IntStream.range(0, res.maxDepth())
                        .mapToObj(i -> CompletableFuture.runAsync(() -> {
                            this.onResponseView(res);
                            final List<SearchResponse> list = this.searcher().search(res);
                            list.forEach(this::onResponseView);
                            System.out.println(list.size());
                            System.out.println("Depth: " + res.currentDepth());
                        }))).toList();

            final CompletableFuture<Void> allNestedFutures = CompletableFuture.allOf(nestedFutures.toArray(new CompletableFuture[0]));
            allNestedFutures.join();
            return null;
        });

        promise.onComplete(handler -> {
            this.onFinishListener();
        });
    }

    @Override
    public void stop() {
        this.vertx.close();
    }

}
