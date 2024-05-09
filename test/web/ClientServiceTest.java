package web;

import ex2.web.Server;
import ex2.web.client.ClientService;
import ex2.web.client.ClientServiceFactory;
import org.junit.Before;
import org.junit.Test;

public class ClientServiceTest {
    private final Server server = new Server();
    private ClientService clientService;

    @Before
    public void setUp() {
//        this.clientService = ClientServiceFactory.createVertx();
        this.clientService = ClientServiceFactory.createJsoup();
        this.server.run();
    }

    @Test
    public void searchLocalUrl() {
        this.clientService.onSearch("http://localhost:8080/index.html");
    }

}
