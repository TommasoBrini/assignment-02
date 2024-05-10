package ex2.web.client;

import org.jsoup.nodes.Document;

public interface ClientService {

    Document findDocument(final String url);

    void close();
}
