package web;

import ex2.utils.JsoupUtils;
import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.junit.Before;
import org.junit.Test;

import static ex2.utils.PathUtils.*;
import static org.junit.Assert.assertEquals;

public class ClientServiceTest {
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
        this.clientService.findUrl(LOCAL_URL);
    }

    @Test
    public void searchRemoteUrl() {
        this.clientService.findUrl(REMOTE_URL);
    }

    @Test
    public void searchInvalidUrl() {
        assertEquals(JsoupUtils.EmptyDocument, this.clientService.findUrl(INVALID_URL));
    }

    @Test
    public void massiveSearchLocalUrl() {
        final int requests = 1000;
        for (int i = 0; i < requests; i++) {
            this.clientService.findUrl(LOCAL_URL);
        }
    }

    @Test
    public void massiveSearchRemoteUrl() {
        final int requests = 100;
        for (int i = 0; i < requests; i++) {
            this.clientService.findUrl(REMOTE_URL);
        }
    }

}
