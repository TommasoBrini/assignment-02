package ex2.web.client;

import org.jsoup.nodes.Document;

public interface ClientService {

    Document findUrl(final String url);

    void close();
}
