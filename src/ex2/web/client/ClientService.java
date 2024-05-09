package ex2.web.client;

import org.jsoup.nodes.Document;

public interface ClientService {

    void addListener(final ClientListener listener);

    Document onSearch(final String url);

    void clearListener();

    void close();
}
