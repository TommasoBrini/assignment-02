package ex2.worker.concrete;

import ex2.core.event.SearchResponse;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class VirtualThreadsImpl extends AbstractWorker {
    private final ExecutorService executor;

    public VirtualThreadsImpl() {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public Type strategy() {
        return Type.VIRTUAL_THREAD;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.onResponseView(response);
        final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
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
                            }, this.executor))
                    )
                    .toList();

            final CompletableFuture<Void> allNestedFutures = CompletableFuture.allOf(nestedFutures.toArray(new CompletableFuture[0]));
            allNestedFutures.join();
        }, this.executor);

        future.thenRun(this::onFinishListener);
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }
}
