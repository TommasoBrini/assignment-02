package core;

import ex2.core.component.SearchLogic;
import ex2.core.component.Searcher;
import ex2.core.component.concrete.SearcherImpl;
import ex2.core.event.SearchEvent;
import ex2.core.event.SearchEventFactory;
import ex2.core.event.SearchResponse;
import ex2.utils.PathUtils;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import ex2.worker.LogicWorker;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

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
        final SearchEvent searchEvent = SearchEventFactory.create(
                LogicWorker.Type.EVENT_LOOP,
                SearchLogic.Type.LOCAL,
                PathUtils.LOCAL_URL,
                "API",
                0,
                4);
        this.searcher.setup(this.clientService, searchEvent);
        final SearchResponse rootUrl = this.searcher.initSearch();

        this.searcher.search(rootUrl).forEach(url -> {
            assertFalse(this.searcher.search(url).isEmpty());
        });

        assert this.searcher.totalWord() > 0;
        System.out.println(this.searcher.totalWord());
        System.out.println(this.searcher.computeDuration() + " ms");
    }

    @Test
    public void remoteSearch() {
        final SearchEvent searchEvent = SearchEventFactory.create(
                LogicWorker.Type.EVENT_LOOP,
                SearchLogic.Type.REMOTE,
                PathUtils.REMOTE_URL,
                "Google",
                0,
                2);
        this.searcher.setup(this.clientService, searchEvent);
        final SearchResponse rootUrl = this.searcher.initSearch();

        this.searcher.search(rootUrl).forEach(url -> {
            assertFalse(this.searcher.search(url).isEmpty());
        });

        assert this.searcher.totalWord() > 0;
        System.out.println(this.searcher.totalWord());
        System.out.println(this.searcher.computeDuration() + " ms");
    }
}
