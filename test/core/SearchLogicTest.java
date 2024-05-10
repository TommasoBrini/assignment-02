package core;

import ex2.core.component.SearchLogic;
import ex2.core.component.SearchLogicFactory;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ex2.utils.PathUtils.*;
import static org.junit.Assert.*;

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
        final Document document = this.clientService.findDocument(LOCAL_URL);
        this.searchLogic.findUrls(document).forEach(System.out::println);
    }

    @Test
    public void findRemoteUrls() {
        this.searchLogic = SearchLogicFactory.createRemote();
        final Document document = this.clientService.findDocument(REMOTE_URL);
        this.searchLogic.findUrls(document).forEach(System.out::println);
    }

    @Test
    public void findLocalUrlsFromRemoteInvalidUlr() {
        this.searchLogic = SearchLogicFactory.createRemote();
        final Document document = this.clientService.findDocument(INVALID_URL);
        assertEquals(0, this.searchLogic.findUrls(document).size());
    }

    @Test
    public void findLocalUrlsFromLocalValidUlr() {
        this.searchLogic = SearchLogicFactory.createLocal();
        final Document document = this.clientService.findDocument(INVALID_URL);
        assertEquals(0, this.searchLogic.findUrls(document).size());
    }

    @Test
    public void findLocalUrlsDepth2() {
        this.searchLogic = SearchLogicFactory.createLocal();
        final Document document = this.clientService.findDocument(LOCAL_URL);
        final List<String> urlsDepth1 = this.searchLogic.findUrls(document);

        urlsDepth1.stream().findFirst().ifPresent(url -> {
            final Document documentDepth1 = this.clientService.findDocument(url);
            final List<String> urlDepth2 = this.searchLogic.findUrls(documentDepth1);
            urlDepth2.forEach(url1 -> assertFalse(urlsDepth1.contains(url1)));
        });
    }

}
