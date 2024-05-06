package ex2.gui;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.Searcher;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.InputGuiListener;
import ex2.core.listener.ViewListener;
import ex2.gui.area.AnalysisArea;
import ex2.gui.area.CommandArea;
import ex2.gui.area.HistoryArea;
import ex2.gui.area.PrintArea;
import ex2.utils.MessageDialogUtils;
import ex2.worker.concrete.WorkerStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static ex2.gui.GuiConstants.FRAME_DIMENSION;

public class GUISearchWord extends JFrame implements ViewListener {
    private final CommandArea commandArea;
    private final PrintArea printArea;
    private final HistoryArea historyArea;

    public GUISearchWord() {
        super("Search Word");
        this.commandArea = new CommandArea();
        this.printArea = new PrintArea();
        this.historyArea = new HistoryArea(this.commandArea);

        this.setSize(FRAME_DIMENSION);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.commandArea.addInputListener(this.printArea);
        this.commandArea.addInputListener(this.historyArea);

        final AnalysisArea area = new AnalysisArea();

        this.setupGraphics();
        this.setResizable(true);
    }

    private void setupGraphics() {
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH, this.commandArea);
        this.add(BorderLayout.EAST, this.historyArea);
        this.add(BorderLayout.CENTER, this.printArea);
    }

    public void start(final List<DataEvent> history) {
        history.forEach(data -> this.historyArea.append(data.workerStrategy(), data.searcherType(), data.url(), data.word(), "" + data.maxDepth()));
        this.setVisible(true);
    }

    public WorkerStrategy getWorkerStrategy() {
        return this.commandArea.workerStrategy();
    }

    public void addInputGuiListener(final InputGuiListener inputGuiListener) {
        this.commandArea.addInputListener(inputGuiListener);
    }

    @Override
    public void onResponse(final Searcher searcher) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("ADD " + searcher);
            final String url = "URL: %s\n".formatted(searcher.url());
            final String info = "Depth[%d] -------- Word = %d\n".formatted(searcher.currentDepth(), searcher.countWord());
            this.printArea.append(url + info + "--------------------\n");
        });
    }

    @Override
    public void onError(final String message) {
        SwingUtilities.invokeLater(() -> MessageDialogUtils.createError(this, "ERROR: %s".formatted(message)));
    }

    @Override
    public void onFinish() {
        SwingUtilities.invokeLater(() -> {
            this.commandArea.enableCommand();
            MessageDialogUtils.createMessage(this, "Finish Search");
        });
    }


}
