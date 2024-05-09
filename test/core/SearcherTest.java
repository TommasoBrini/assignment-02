package core;

import ex2.core.component.searcher.SearchLogic;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherImpl;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ex2.utils.PathUtils.LOCAL_URL;
import static ex2.utils.PathUtils.REMOTE_URL;

public class SearcherTest {
    private final Server server = new Server();
    private String word;
    private Searcher searcher;
    private ClientService clientService;

    @Before
    public void setup() {
        this.server.run();
        this.searcher = new SearcherImpl();
        this.clientService = ClientServiceFactory.createVertx();
    }

    @Test
    public void localSearch() {
        this.searcher.setup(this.clientService, SearchLogic.Type.LOCAL, LOCAL_URL, "Java");
        final List<String> urls = this.searcher.initSearch();
        urls.forEach(url -> this.searcher.search(url));
        System.out.println(this.searcher.totalWord());
        System.out.println(this.searcher.computeDuration() + " ms");
    }

    @Test
    public void remoteSearch() {
        this.searcher.setup(this.clientService, SearchLogic.Type.REMOTE, REMOTE_URL, "Google");
        final List<String> urls = this.searcher.initSearch();
        urls.forEach(url -> this.searcher.search(url));
        System.out.println(this.searcher.totalWord());
        System.out.println(this.searcher.computeDuration() + " ms");
    }
}
