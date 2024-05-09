package ex2.web.client;

import ex2.core.component.DataEvent;
import org.jsoup.nodes.Document;

public interface ClientListener {

    void onResponse(final DataEvent dataEvent, final Document doc, final long startTime);
}
