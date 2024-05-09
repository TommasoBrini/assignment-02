package ex2.web.client;

public interface ClientService {

    void addListener(final ClientListener listener);

    void onSearch(final String url);

    void clearListener();

    void close();
}
