package ex2.eventLoop.core;

import ex2.eventLoop.searcher.Searcher;
import ex2.eventLoop.searcher.SearcherImpl;
import ex2.gui.ViewListener;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.uritemplate.UriTemplate;

import java.util.ArrayList;
import java.util.List;

public class EventLoopImpl extends AbstractVerticle implements EventLoop, WorkerLoop {
    private final List<ViewListener> viewListeners;

    public EventLoopImpl() {
        this.init(Vertx.vertx(), null);
        this.viewListeners = new ArrayList<>();
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
    public void searchUrl(final String url, final String word, final int depth) {
        final WebClient webClient = WebClient.create(this.vertx);

        webClient.getAbs(UriTemplate.of(url))
                .send(handler -> {
                    if (handler.succeeded()) {
                        // Gestione della risposta ricevuta
                        System.out.println("Response status code: " + handler.result().statusCode());
                        System.out.println("Response body:");
                        System.out.println(handler.result().body());

                        final Searcher filter = new SearcherImpl(this, url, handler.result().bodyAsString(), word, depth);
                        this.viewListeners.forEach(listener -> listener.onResponse(filter));
                    } else {
                        // Gestione degli errori
                        System.err.println("Request failed: " + handler.cause().getMessage());
                        this.viewListeners.forEach(viewListener -> viewListener.onError(handler.cause().getMessage()));
                    }
                });
    }

}
