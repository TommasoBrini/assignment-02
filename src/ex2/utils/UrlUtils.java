package ex2.utils;

import java.net.MalformedURLException;
import java.net.URL;

public final class UrlUtils {

    public static boolean isValidURL(final String urlString) {
        try {
            final URL url = new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
