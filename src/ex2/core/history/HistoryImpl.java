package ex2.core.history;

import ex2.core.dataEvent.DataEvent;
import ex2.core.dataEvent.DataEventImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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
            // Leggi il contenuto del file come array di byte
            byte[] fileData = Files.readAllBytes(Paths.get(HISTORY_PATH));

            // Converti i byte in una stringa JSON
            String jsonString = new String(fileData);

            // Converti la stringa JSON in un JsonObject
            JsonObject jsonObject = new JsonObject(jsonString);

            // Estrai il JsonArray dalla chiave "people"
            JsonArray peopleArray = jsonObject.getJsonArray(HISTORY_KEY);

            if (peopleArray != null && !peopleArray.isEmpty()) {
                // Itera su ciascun elemento nel JsonArray
                for (final Object obj : peopleArray) {
                    if (obj instanceof JsonObject) {
                        dataEvents.add(new DataEventImpl((JsonObject) obj));
                    }
                }
                // Stampa le persone lette da JSON
                System.out.println("Persone lette da JSON:");
            } else {
                System.err.println("Il JsonArray 'people' è vuoto o non presente nel file persona.json");
            }
        } catch (Exception e) {
            System.err.println("Errore durante la lettura del file persona.json");
            e.printStackTrace();
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

        try {
            String jsonString = jsonObject.encodePrettily();
            Files.write(Paths.get(HISTORY_PATH), jsonString.getBytes());
            System.out.println("File persona.json salvato correttamente");
        } catch (Exception e) {
            System.err.println("Errore durante la scrittura del file persona.json");
            e.printStackTrace();
        }
    }
}

