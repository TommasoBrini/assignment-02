package ex2.core.history;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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

        this.readJSON().forEach(System.out::println);

    }

    private List<DataEvent> readJSON() {
        final List<DataEvent> dataEvents = new ArrayList<>();
        final Vertx vertx = Vertx.vertx();
        final FileSystem fileSystem = vertx.fileSystem();

        // Leggi il contenuto del file persona.json
        fileSystem.readFile(HISTORY_PATH, result -> {
            if (result.succeeded()) {
                final JsonObject jsonObject = result.result().toJsonObject();
                final List jsonArray = jsonObject.getJsonArray(HISTORY_KEY).getList();

//                jsonArray.forEach(json -> dataEvents.add(new DataEventImpl((JsonObject) json)));
//                jsonArray.forEach(json -> dataEvents.add(new DataEventImpl(json)));

                for (final Object obj : jsonArray) {
                    if (obj instanceof JsonObject) {
                        final JsonObject dataEventJson = (JsonObject) obj;
                        dataEvents.add(new DataEventImpl(dataEventJson));
                    }
                }


            } else {
                System.err.println("Errore durante la lettura del file persona.json");
                result.cause().printStackTrace();
            }
            vertx.close();
        });

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

        final Vertx vertx = Vertx.vertx();
        final FileSystem fileSystem = vertx.fileSystem();

        fileSystem.writeFile(HISTORY_PATH, jsonObject.toBuffer(), result -> {
            if (result.succeeded()) {
                System.out.println("File salvato correttamente");
            } else {
                System.err.println("Errore durante il salvataggio del file persona.json");
                result.cause().printStackTrace();
            }
            vertx.close();
        });
    }
}

