package ex2.gui.area;

import ex2.listener.CommandListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static ex2.gui.GuiConstants.PRINT_AREA_DIMENSION;

public class PrintArea extends JScrollPane implements CommandListener {
    private static final String TITLE_PRINT_AREA = "Print Area";
    private final JTextArea printArea;

    public PrintArea() {
        this.printArea = new JTextArea();
        this.printArea.setEditable(false);
        this.printArea.setPreferredSize(PRINT_AREA_DIMENSION);

        final TitledBorder titleFindWordArea = BorderFactory.createTitledBorder(TITLE_PRINT_AREA);
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
