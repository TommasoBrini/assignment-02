package web;

import ex2.utils.JsoupUtils;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientServiceTest {
    private static final String LOCAL_URL = "http://localhost:8080/index.html";
    private static final String REMOTE_URL = "https://www.google.com";
    private static final String INVALID_URL = "https://www.google.com/invalid";
    private final Server server = new Server();
    private ClientService clientService;

    @Before
    public void setUp() {
        this.clientService = ClientServiceFactory.createVertx();
//        this.clientService = ClientServiceFactory.createJsoup();
        this.server.run();
    }

    @Test
    public void searchLocalUrl() {
        this.clientService.onSearch(LOCAL_URL);
    }

    @Test
    public void searchRemoteUrl() {
        this.clientService.onSearch(REMOTE_URL);
    }

    @Test
    public void searchInvalidUrl() {
        assertEquals(JsoupUtils.EmptyDocument, this.clientService.onSearch(INVALID_URL));
    }

    @Test
    public void massiveSearchLocalUrl() {
        final int requests = 1000;
        for (int i = 0; i < requests; i++) {
            this.clientService.onSearch(LOCAL_URL);
        }
    }

    @Test
    public void massiveSearchRemoteUrl() {
        final int requests = 100;
        for (int i = 0; i < requests; i++) {
            this.clientService.onSearch(REMOTE_URL);
        }
    }

}
