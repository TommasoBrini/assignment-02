package ex2.utils;

import ex2.web.Server;

public final class PathUtils {
    public static final String RES_PATH = "res/";

    public static final String HISTORY_PATH = RES_PATH + "history.json";
    public static final String BLACK_LIST_PATH = RES_PATH + "blackList.json";

    public static final String LOCAL_URL = Server.LOCAL_PATH + "index.html";
    public static final String REMOTE_URL = "https://www.google.com";
    public static final String INVALID_URL = "https://www.google.com/invalid";

}
