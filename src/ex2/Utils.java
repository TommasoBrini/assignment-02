package ex2;

import java.net.MalformedURLException;
import java.net.URL;

public final class Utils {

    public static boolean isValidURL(final String urlString) {
        try {
            final URL url = new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
