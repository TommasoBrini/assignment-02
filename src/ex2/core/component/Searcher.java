package ex2.core.component;

import ex2.core.listener.ViewListener;
import ex2.web.client.ClientService;

import java.util.List;

public interface Searcher {

    void addListener(final ViewListener viewListener);

    void setup(final ClientService clientService, final String url, final SearchLogic.Type searchLogicType, final String word);

    List<String> initSearch();

    List<String> search(final String url);

    int totalWord();

    long computeDuration();
}
