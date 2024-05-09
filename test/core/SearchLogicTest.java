package core;

import ex2.core.SearchLogic;
import ex2.core.SearchLogicFactory;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

public class SearchLogicTest {
    private static final String LOCAL_URL = Server.LOCAL_PATH + "index.html";
    private static final String REMOTE_URL = "https://www.google.com";
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
        final Document document = this.clientService.onSearch(LOCAL_URL);
        this.searchLogic.findUrls(document).forEach(System.out::println);
    }

    @Test
    public void findRemoteUrls() {
        this.searchLogic = SearchLogicFactory.createRemote();
        final Document document = this.clientService.onSearch(REMOTE_URL);
        this.searchLogic.findUrls(document).forEach(System.out::println);
    }

}
