package core;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.component.searcher.SearcherType;
import ex2.web.clientManager.ClientManager;
import ex2.web.clientManager.ClientManagerImpl;
import ex2.worker.concrete.WorkerStrategy;
import org.junit.Test;

public class SearchTest {
    public static final String LOCAL_URL = "http://localhost:8080/index.html";
    public static final String WEB_URL = "https://en.wikipedia.org/wiki/Buongiorno#";

    @Test
    public void findHrefLocal() {
        final ClientManager clientManager = new ClientManagerImpl();
        final DataEvent dataEvent = new DataEventImpl(
                WorkerStrategy.EVENT_LOOP,
                SearcherType.LOCAL,
                LOCAL_URL,
                "API",
                4,
                0);
        clientManager.startSearch(dataEvent);

        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findHrefWeb() {
        final ClientManager clientManager = new ClientManagerImpl();
        final String word = "Buongiorno";
        final int maxDepth = 6;
        final DataEvent dataEvent = new DataEventImpl(
                WorkerStrategy.EVENT_LOOP,
                SearcherType.LOCAL,
                WEB_URL,
                word,
                maxDepth,
                0);
        clientManager.startSearch(dataEvent);

        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
