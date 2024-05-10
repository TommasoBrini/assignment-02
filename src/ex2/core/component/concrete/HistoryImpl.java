package ex2.core.component.concrete;

import ex2.core.event.DataEvent;
import ex2.core.component.History;
import ex2.core.event.DataEventImpl;
import ex2.core.listener.HistoryListener;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ex2.utils.PathUtils.HISTORY_PATH;

public class HistoryImpl implements History {
    private static final String HISTORY_KEY = "history";
    private static final int POSITION_ADD_JSON = 0;
    private static final int MAX_SIZE = 5;

    private final JsonArray historyJson;
    private final List<DataEvent> history;
    private final List<HistoryListener> listeners;

    private Optional<DataEvent> searchEvent;
    private long time;

    public HistoryImpl(final List<HistoryListener> listeners) {
        this.historyJson = new JsonArray();
        this.history = new ArrayList<>();
        this.searchEvent = Optional.empty();
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
                    .forEach(obj -> this.append(new DataEventImpl((JsonObject) obj)));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void append(final DataEvent dataEvent) {
        this.historyJson.add(POSITION_ADD_JSON, dataEvent.toJson());
        this.history.addFirst(dataEvent);
        this.listeners.forEach(listener -> listener.append(dataEvent));
    }

    @Override
    public void addListener(final HistoryListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public List<DataEvent> history() {
        return this.history;
    }

    @Override
    public List<DataEvent> lastHistory() {
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
    public void onStart(final DataEvent event) {
        this.time = System.currentTimeMillis();
        this.searchEvent = Optional.of(event);
    }

    @Override
    public void onFinish() {
        this.searchEvent.ifPresent(event -> {
            final long duration = System.currentTimeMillis() - this.time;
            final DataEvent dataEvent = new DataEventImpl(event.workerStrategy(), event.searcherType(), event.url(), event.word(), event.maxDepth(), event.currentDepth(), duration);
            this.append(dataEvent);
        });
    }
}

