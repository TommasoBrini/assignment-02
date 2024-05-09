package ex2.core.component.searcher;

import ex2.web.client.ClientService;

import java.util.List;

public interface Searcher {

    void setup(final ClientService clientService, final SearchLogic.Type searchLogicType, final String url, final String word);

    List<String> initSearch();

    List<String> search(final String url);

    int totalWord();

    long computeDuration();
}
