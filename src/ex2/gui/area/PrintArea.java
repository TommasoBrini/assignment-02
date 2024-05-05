package ex2.gui.area;

import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.InputGuiListener;
import ex2.worker.concrete.WorkerStrategy;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import static ex2.gui.GuiConstants.PRINT_AREA_DIMENSION;

public class PrintArea extends JScrollPane implements InputGuiListener {
    private static final String TITLE_PRINT_AREA = "Print Area";
    private final JTextArea printArea;

    public PrintArea() {
        this.printArea = new JTextArea();
        this.printArea.setEditable(false);
        this.setPreferredSize(PRINT_AREA_DIMENSION);

        final TitledBorder titleFindWordArea = BorderFactory.createTitledBorder(TITLE_PRINT_AREA);
        titleFindWordArea.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(titleFindWordArea);

        this.setViewportView(this.printArea);
    }

    public void append(final String text) {
        this.printArea.append(text);
    }

    @Override
    public void onSearch(WorkerStrategy workerStrategy, final SearcherType searcherType, final String site, final String word, final int maxDepth) {
        this.printArea.setText("");
    }

    @Override
    public void onExit() {

    }

}
