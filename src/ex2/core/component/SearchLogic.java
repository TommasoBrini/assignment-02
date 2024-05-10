package ex2.core.component;

import org.jsoup.nodes.Document;

import java.util.List;

public interface SearchLogic {
    enum Type {
        LOCAL,
        REMOTE
    }

    List<String> findUrls(final Document document);

    Type type();
}
