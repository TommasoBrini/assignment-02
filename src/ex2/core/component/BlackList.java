package ex2.core.component;

public interface BlackList {

    void append(final String url);

    boolean isInBlackList(final String url);

}
