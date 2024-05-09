package ex2.core;

import ex2.web.client.ClientService;

import java.util.List;

public interface Searcher {

    List<String> initSearch(final ClientService clientService, final SearchLogic.Type searchLogicType, final String url);

    List<String> search(final ClientService clientService, final SearchLogic.Type searchLogicType, final String url);

    int totalWord();

    long computeDuration();
}
