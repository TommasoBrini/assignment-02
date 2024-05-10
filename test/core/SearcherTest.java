package core;

import ex2.core.component.SearchLogic;
import ex2.core.component.Searcher;
import ex2.core.component.concrete.SearcherImpl;
import ex2.utils.PathUtils;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SearcherTest {
    private final Server server = new Server();
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
        this.searcher.setup(this.clientService, PathUtils.LOCAL_URL, SearchLogic.Type.LOCAL, "Java");
        final List<String> urls = this.searcher.initSearch();
        urls.forEach(url -> this.searcher.search(url));
        System.out.println(this.searcher.totalWord());
        System.out.println(this.searcher.computeDuration() + " ms");
    }

    @Test
    public void remoteSearch() {
        this.searcher.setup(this.clientService, PathUtils.REMOTE_URL, SearchLogic.Type.REMOTE, "Google");
        final List<String> urls = this.searcher.initSearch();
        urls.forEach(url -> this.searcher.search(url));
        System.out.println(this.searcher.totalWord());
        System.out.println(this.searcher.computeDuration() + " ms");
    }
}
