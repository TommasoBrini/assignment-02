package ex2.web.client;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ClientServiceFactory {

    public static ClientService createJsoup() {
        return new JsoupService();
    }

    public static ClientService createVertx() {
        return new VertxService();
    }

    private static abstract class AbstractService {
        private final List<ClientListener> listeners;
        public AbstractService() { this.listeners = new ArrayList<>(); }
        protected List<ClientListener> listeners() { return this.listeners; }
        public void addListener(final ClientListener listener) { this.listeners.add(listener); }
        public void clearListener() { this.listeners.clear(); }
    }

    private static class JsoupService extends AbstractService implements ClientService {
        private final Connection session;

        public JsoupService() {
            this.session = Jsoup.newSession();
        }

        @Override
        public void onSearch(final String url) {
            try {
                final Document doc = this.session.newRequest(url).get();
            } catch (final IOException e) {
                System.out.println(url + " is not found");
                throw new RuntimeException(e);
            }
        }

        @Override
        public void close() {
            // TODO: find fun for closing session
        }
    }

    private static class VertxService extends AbstractService implements ClientService {
        private final WebClient webClient;
        private final Vertx vertx;

        public VertxService() {
            this.vertx = Vertx.vertx();
            this.webClient = WebClient.create(this.vertx);
        }

        @Override
        public void onSearch(final String url) {
            this.webClient.getAbs(url).send(response -> {
                if (response.succeeded()) {
                    final Document doc = Jsoup.parse(response.result().bodyAsString());
                } else {
                    System.out.println(url + " is not found");
                    throw new RuntimeException(response.cause());
                }
            });
        }

        @Override
        public void close() {
            this.vertx.close();
        }
    }
}
