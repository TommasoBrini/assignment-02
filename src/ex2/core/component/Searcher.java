package ex2.core.component;

import ex2.core.event.SearchEvent;
import ex2.core.event.SearchResponse;
import ex2.web.client.ClientService;

import java.util.List;

public interface Searcher {

    void setup(final ClientService clientService, final SearchEvent searchEvent);

    SearchResponse initSearch();

    List<SearchResponse> search(final SearchResponse response);

    int totalWord();

    long computeDuration();
}
