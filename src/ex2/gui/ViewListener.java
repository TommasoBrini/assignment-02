package ex2.gui;

public interface ViewListener {

    void onResponse(String bodyPage);

    void onError(String message);
}
