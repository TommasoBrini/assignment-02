package ex2.worker.concrete;

import ex2.core.event.SearchResponse;

import java.util.ArrayList;
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
        Future<?> future = executor.submit(
                () -> {
                    final List<SearchResponse> responses = this.searcher().search(response);
                    this.onResponseView(response);

                    IntStream.range(0, response.maxDepth()).forEach(i ->
                            responses.forEach(res -> {
                                System.out.println(res);
                                //mappo tutti i response in una lista di future
                                final List<Future<?>> futures = new ArrayList<>();
                                futures.add(this.executor.submit(() -> {
                                    final List<SearchResponse> list = this.searcher().search(res);
                                    list.forEach(this::onResponseView);
                                    System.out.println(list.size());
                                    System.out.println("Depth: " + res.currentDepth());
                                }));
                                //controllo che le futures siano terminate
                                this.waitForAllFutures(futures);
                            })
                    );
                }
        );

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            this.onFinishListener();
        }
        //this.waitForAllFutures(List.of(future));

                /*List.of(this.executor.submit(() -> {
            final List<SearchResponse> responses = this.searcher().search(response);
            this.onResponseView(response);
            responses.forEach(this::addEventUrl);
        }));
        //controllo che le futures siano terminate
        this.waitForAllFutures(futures);*/
    }

    private void waitForAllFutures(final List<Future<?>> futures) {
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void stop() {
        this.executor.shutdown();
    }
}
