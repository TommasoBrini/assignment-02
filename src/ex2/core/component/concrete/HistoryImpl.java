package ex2.core.component.concrete;

import ex2.core.component.Searcher;
import ex2.core.event.SearchData;
import ex2.core.component.History;
import ex2.core.event.SearchResponse;
import ex2.core.event.factory.SearchDataFactory;
import ex2.core.listener.HistoryListener;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static ex2.utils.PathUtils.HISTORY_PATH;

public class HistoryImpl implements History {
    private static final String HISTORY_KEY = "history";
    private static final int POSITION_ADD_JSON = 0;
    private static final int MAX_SIZE = 5;

    private final JsonArray historyJson;
    private final List<SearchData> history;
    private final List<HistoryListener> listeners;

    public HistoryImpl(final List<HistoryListener> listeners) {
        this.historyJson = new JsonArray();
        this.history = new ArrayList<>();
        this.listeners = new ArrayList<>(listeners);
        this.createJSON();
        this.readJSON();
    }

    public HistoryImpl() {
        this(List.of());
    }

    private void createJSON() {
        final Path path = Paths.get(HISTORY_PATH);
        try {
            if (!Files.exists(path)) Files.createFile(path);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readJSON() {
        try {
            final String jsonString = Files.readString(Paths.get(HISTORY_PATH));
            if (jsonString.isBlank()) return;
            final JsonObject jsonObject = new JsonObject(jsonString);
            final JsonArray history = jsonObject.getJsonArray(HISTORY_KEY);

            history.stream()
                    .filter(obj -> obj instanceof JsonObject)
                    .forEach(obj -> this.append(SearchDataFactory.create((JsonObject) obj)));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void append(final SearchData searchData) {
        this.historyJson.add(POSITION_ADD_JSON, searchData.toJson());
        this.history.addFirst(searchData);
        this.listeners.forEach(listener -> listener.append(searchData));
    }

    @Override
    public void addListener(final HistoryListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public List<SearchData> history() {
        return this.history;
    }

    @Override
    public List<SearchData> lastHistory() {
        final int skip = Math.max(this.history.size() - MAX_SIZE, 0);
        return this.history.stream().skip(skip).toList();
    }

    @Override
    public void saveJSON() {
        final JsonObject jsonObject = new JsonObject().put(HISTORY_KEY, this.historyJson);
        final String jsonString = jsonObject.encodePrettily();
        try {
            Files.write(Paths.get(HISTORY_PATH), jsonString.getBytes());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart(final SearchResponse event) {

    }

    @Override
    public void onFinish(final Searcher searcher) {
        final SearchData searchData = searcher.dataOnFinish();
        this.append(searchData);
    }
}

