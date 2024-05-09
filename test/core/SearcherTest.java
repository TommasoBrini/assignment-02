package core;

import ex2.core.SearchLogic;
import ex2.core.Searcher;
import ex2.core.SearcherImpl;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ex2.utils.PathUtils.LOCAL_URL;

public class SearcherTest {
    private final Server server = new Server();
    private String word;
    private Searcher searcher;
    private ClientService clientService;

    @Before
    public void setup() {
        this.server.run();
        this.word = "Java";
        this.searcher = new SearcherImpl(this.word);
        this.clientService = ClientServiceFactory.createVertx();
    }

    @Test
    public void localSearch() {
        final List<String> urls = this.searcher.initSearch(this.clientService, SearchLogic.Type.LOCAL, LOCAL_URL);
        urls.forEach(url -> this.searcher.search(this.clientService, SearchLogic.Type.LOCAL, url));
        System.out.println(this.searcher.totalWord());
    }
}
