package ex2.server.client;

import ex2.core.component.DataEvent;

public interface ClientService {

    void addListenerRequest(ClientListener listener);

    void searchUrl(final DataEvent dataEvent);

    void clearListener();
}
