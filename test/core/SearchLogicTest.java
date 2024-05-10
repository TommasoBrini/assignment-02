package core;

import ex2.core.component.SearchLogic;
import ex2.core.component.SearchLogicFactory;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import static ex2.utils.PathUtils.*;
import static org.junit.Assert.assertEquals;

public class SearchLogicTest {
    private final Server server = new Server();
    private SearchLogic searchLogic;
    private ClientService clientService;

    @Before
    public void setUp() {
        this.server.run();
        this.clientService = ClientServiceFactory.createJsoup();
    }

    @Test
    public void findLocalUrls() {
        this.searchLogic = SearchLogicFactory.createLocal();
        final Document document = this.clientService.findUrl(LOCAL_URL);
        this.searchLogic.findUrls(document).forEach(System.out::println);
    }

    @Test
    public void findRemoteUrls() {
        this.searchLogic = SearchLogicFactory.createRemote();
        final Document document = this.clientService.findUrl(REMOTE_URL);
        this.searchLogic.findUrls(document).forEach(System.out::println);
    }

    @Test
    public void findLocalUrlsFromRemoteInvalidUlr() {
        this.searchLogic = SearchLogicFactory.createRemote();
        final Document document = this.clientService.findUrl(INVALID_URL);
        assertEquals(0, this.searchLogic.findUrls(document).size());
    }

    @Test
    public void findLocalUrlsFromLocalValidUlr() {
        this.searchLogic = SearchLogicFactory.createLocal();
        final Document document = this.clientService.findUrl(INVALID_URL);
        assertEquals(0, this.searchLogic.findUrls(document).size());
    }

}
