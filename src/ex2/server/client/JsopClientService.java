package ex2.server.client;

import ex2.core.component.DataEvent;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsopClientService implements ClientService {
    private final List<ClientListener> listeners;
    private final Connection session;

    public JsopClientService() {
        this.listeners = new ArrayList<>();
        this.session = Jsoup.newSession();
    }

    @Override
    public void addListenerRequest(final ClientListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void searchUrl(final DataEvent dataEvent) {
        try {
            final Document doc = this.session.url(dataEvent.url()).followRedirects(true).get();
            this.listeners.forEach(listener ->
                    listener.onResponse(dataEvent, doc, System.currentTimeMillis()));
        } catch (final IOException e) {
            System.out.println(dataEvent.url() + " is not found");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearListener() {
        this.listeners.clear();
    }
}
