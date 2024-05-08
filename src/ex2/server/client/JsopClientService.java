package ex2.server.client;

import ex2.core.component.DataEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsopClientService implements ClientService {
    private final List<ClientListener> listeners;

    public JsopClientService() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListenerRequest(final ClientListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void searchUrl(final DataEvent dataEvent) {
        try {
            final Document doc = Jsoup.connect(dataEvent.url()).get();
            this.listeners.forEach(listener ->
                    listener.onResponse(dataEvent, doc, System.currentTimeMillis()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearListener() {
        this.listeners.clear();
    }
}
