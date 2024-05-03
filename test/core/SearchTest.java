package core;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import ex2.core.searcher.SearcherType;
import ex2.worker.WorkerManager;
import ex2.worker.WorkerManagerImpl;
import org.junit.Test;

public class SearchTest {
    public static final String LOCAL_URL = "http://localhost:8080/index.html";
    public static final String WEB_URL = "https://en.wikipedia.org/wiki/Buongiorno#";

    @Test
    public void findHrefLocal() {
        final WorkerManager workerManager = new WorkerManagerImpl(SearcherType.LOCAL);
        final DataEvent dataEvent = new DataEventImpl(LOCAL_URL, "API", 4, 0);
        workerManager.startSearch(dataEvent);

        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findHrefWeb() {
        final WorkerManager workerManager = new WorkerManagerImpl(SearcherType.WEB);
        final String word = "Buongiorno";
        final DataEvent dataEvent = new DataEventImpl(WEB_URL, word, 2, 0);
        workerManager.startSearch(dataEvent);

        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
