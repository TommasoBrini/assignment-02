package ex2.gui.area;

import ex2.eventLoop.searcher.Searcher;
import ex2.listener.CommandListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PrintArea extends JScrollPane implements CommandListener {
    private static final Dimension FIND_WORD_AREA_SIZE = new Dimension(380, 380);
    private static final String TITLE_FIND_WORD_AREA = "Find Word";

    private final JTextArea printArea;

    public PrintArea() {
        this.printArea = new JTextArea();
        this.printArea.setEditable(false);
        this.printArea.setPreferredSize(FIND_WORD_AREA_SIZE);

        final TitledBorder titleFindWordArea = BorderFactory.createTitledBorder(TITLE_FIND_WORD_AREA);
        titleFindWordArea.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(titleFindWordArea);

        this.setViewportView(this.printArea);
    }

    public void append(final String text) {
        this.printArea.setForeground(Color.BLACK);
        this.printArea.append(text);
    }

    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int maxDepth) {
        this.printArea.setText("");
    }

    @Override
    public void onExit() {

    }

}
