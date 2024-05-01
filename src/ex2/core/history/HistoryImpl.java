package ex2.core.history;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoryImpl implements History {
    private static final String HISTORY_PATH = "res/history.json";
    private static final String HISTORY_KEY = "history";

    private final JsonArray historyJson;
    private final List<DataEvent> history;

    public HistoryImpl() {
        this.historyJson = new JsonArray();
        this.history = new ArrayList<>();

        final DataEvent data = new DataEventImpl("Ciao", "dd", 0, 3);
        final DataEvent data1 = new DataEventImpl("come", "dd", 0, 3);
        final DataEvent data2 = new DataEventImpl("stai", "dd", 0, 3);

        List.of(data, data1, data2).forEach(this::append);

        this.saveJSON();

        System.out.println("CCCCC");
        this.readJSON().forEach(System.out::println);

    }

    private List<DataEvent> readJSON() {
        final List<DataEvent> dataEvents = new ArrayList<>();
        try {
            final String jsonString = Files.readString(Paths.get(HISTORY_PATH));
            final JsonObject jsonObject = new JsonObject(jsonString);
            final JsonArray history = jsonObject.getJsonArray(HISTORY_KEY);

            history.forEach(System.out::println);
            history.stream()
                    .filter(obj -> obj instanceof JsonObject)
                    .forEach(obj -> dataEvents.add(new DataEventImpl((JsonObject) obj)));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        return dataEvents;
    }

    @Override
    public void append(final DataEvent dataEvent) {
        this.historyJson.add(dataEvent.toJson());
        this.history.add(dataEvent);
    }

    @Override
    public List<DataEvent> history() {
        return this.history;
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
}

