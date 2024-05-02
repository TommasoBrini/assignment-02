package core;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import ex2.core.searcher.SearcherId;
import ex2.server.Server;
import ex2.worker.LogicWorker;
import ex2.worker.eventLoop.EventLoopImpl;
import org.junit.Test;

public class SearchLocalTest {
    public static final String URL = "http://localhost:8080/index.html";

    @Test
    public void findHref() {
        final Server server = new Server();
        final LogicWorker worker = new EventLoopImpl(SearcherId.LOCAL);
        server.run();
        final DataEvent dataEvent = new DataEventImpl(URL, "API", 4, 0);
        worker.startSearch(dataEvent);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
