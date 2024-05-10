package ex2.web.client;

import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;
import ex2.utils.JsoupUtils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.atomic.AtomicReference;

public final class ClientServiceFactory {

    public static ClientService createJsoup() {
        return new JsoupService();
    }

    public static ClientService createVertx() {
        return new VertxService();
    }

    private static class JsoupService implements ClientService {
        private final Connection session;

        public JsoupService() {
            this.session = Jsoup.newSession();
        }

        @Override
        public Document findDocument(final String url) {
            Document doc = JsoupUtils.EmptyDocument;
            try {
                doc = this.session.newRequest(url).followRedirects(true).get();
            } catch (final Exception ignored) {
            }
            return doc;
        }

        @Override
        public void close() {
            // TODO: find fun for closing session
        }
    }

    private static class VertxService implements ClientService {
        private static final int STATUS_CODE_MIN = 200;
        private static final int STATUS_CODE_MAX = 300;
        private final WebClient webClient;
        private final Vertx vertx;
        private final StartStopMonitor startStopMonitor;

        public VertxService() {
            this.vertx = Vertx.vertx();
            this.webClient = WebClient.create(this.vertx);
            this.startStopMonitor = new StartStopMonitorImpl();
        }

        @Override
        public Document findDocument(final String url) {
            this.startStopMonitor.pause();
            final AtomicReference<Document> document = new AtomicReference<>(JsoupUtils.EmptyDocument);
            this.webClient.getAbs(url).send(response -> {
                final int statusCode = response.result().statusCode();
                if (response.succeeded() && statusCode >= STATUS_CODE_MIN && statusCode < STATUS_CODE_MAX) {
                    document.set(Jsoup.parse(response.result().bodyAsString()));
                }
                this.startStopMonitor.play();
            });
            this.startStopMonitor.awaitUntilPlay();
            return document.get();
        }

        @Override
        public void close() {
            this.vertx.close();
        }
    }
}
