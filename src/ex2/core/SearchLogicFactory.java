package ex2.core;

import ex2.web.Server;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public final class SearchLogicFactory {
    public static SearchLogic createLocal() {
        return new LocalLogic();
    }

    public static SearchLogic createRemote() {
        return new RemoteLogic();
    }

    private static final List<String> extension = List.of(
            ".svg", ".png", ".jpg", ".jpeg", ".gif", ".pdf", ".mp4", ".mp3",
            ".avi", ".flv", ".mov", ".wmv", ".zip", ".dmg", ".exe", ".msi");

    private static boolean checkExtensions(final String url) {
        return extension.stream().noneMatch(url::endsWith);
    }

    private static class LocalLogic implements SearchLogic {
        @Override
        public List<String> findUrls(final Document document) {
            final Elements elements = document.select("body a");
            return new ArrayList<>(elements.stream().map(l -> l.attr("href"))
                    .filter(url -> !url.startsWith("http")
                            && !url.contains("#")
                            && !url.contains("package-summary")
                            && !url.contains("Context")
                            && checkExtensions(url))
                    .map(url -> Server.LOCAL_PATH + url)
                    .toList());
        }

        @Override
        public Type type() {
            return Type.LOCAL;
        }
    }

    private static class RemoteLogic implements SearchLogic {
        @Override
        public List<String> findUrls(final Document document) {
            final Elements elements = document.select("body a");
            return new ArrayList<>(elements.stream().map(l -> l.attr("href"))
                    .filter(url -> url.startsWith("http") && checkExtensions(url))
                    .toList());
        }

        @Override
        public Type type() {
            return Type.REMOTE;
        }
    }
}
