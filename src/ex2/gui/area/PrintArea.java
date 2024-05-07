package ex2.gui.area;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.listener.InputGuiListener;
import ex2.gui.components.WorkerPrint;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import static ex2.gui.GuiConstants.PRINT_AREA_DIMENSION;

public class PrintArea extends JScrollPane implements InputGuiListener {
    private static final String TITLE_PRINT_AREA = "Print Area";
    private static final String EVENT_SEARCH = "Searcher";
    private static final String URL = "url";
    private static final String COUNT_WORD = "countWord";
    private static final String CURRENT_DEPTH = "currentDepth";
    private final JTextArea printArea;
    private final WorkerPrint workerPrint;
//    private final Vertx vertx;

    public PrintArea() {
//        this.vertx = Vertx.vertx();
        this.printArea = new JTextArea();
        this.workerPrint = new WorkerPrint(this);
        this.printArea.setEditable(false);
        this.setPreferredSize(PRINT_AREA_DIMENSION);

        final TitledBorder titleFindWordArea = BorderFactory.createTitledBorder(TITLE_PRINT_AREA);
        titleFindWordArea.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(titleFindWordArea);


        this.setViewportView(this.printArea);
//        this.setupConsumers();
    }

//    private void setupConsumers() {
//        this.vertx.eventBus().consumer(EVENT_SEARCH, handler -> {
//            final JsonObject jsonObject = (JsonObject) handler.body();
//            SwingUtilities.invokeLater(() -> {
//                this.append(jsonObject.getString(URL),
//                        jsonObject.getInteger(COUNT_WORD),
//                        jsonObject.getInteger(CURRENT_DEPTH));
//            });
//        });
//    }

    public void sendEventSearcher(final Searcher searcher) {
        this.workerPrint.addSearcher(searcher);
//        this.vertx.eventBus().send(EVENT_SEARCH, this.searcherToJson(searcher));
    }

    public void append(final String url, final int countWord, final int currentDepth) {
        final String mainUrl = "URL: %s\n".formatted(url);
        final String info = "Depth[%d] -------- Word = %d\n".formatted(currentDepth, countWord);
        final String text = mainUrl + info + "--------------------\n";
        this.printArea.append(text);
    }

//    private JsonObject searcherToJson(final Searcher searcher) {
//        return new JsonObject()
//                .put(URL, searcher.url())
//                .put(COUNT_WORD, searcher.countWord())
//                .put(CURRENT_DEPTH, searcher.currentDepth());
//    }

    @Override
    public void onSearch(final DataEvent dataEvent) {
        this.printArea.setText("");
    }

    @Override
    public void onExit() {
        this.workerPrint.stop();
    }

    public void append(final String text) {
        this.printArea.append(text);
    }
}
