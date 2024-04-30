package ex2.gui;

import ex2.eventLoop.CommandListener;
import ex2.eventLoop.searcher.Searcher;
import ex2.gui.area.CommandArea;
import ex2.gui.area.HistoryArea;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GUISearchWord extends JFrame implements ViewListener {
    private static final Dimension FIND_WORD_AREA_SIZE = new Dimension(380, 380);
    private static final Dimension FRAME_SIZE = new Dimension(800, 500);
    private static final String TITLE_FIND_WORD_AREA = "Find Word";

    private final CommandArea commandArea;
    private final JTextArea findWordArea;
    private final HistoryArea historyArea;


    public GUISearchWord() {
        super("Search Word");
        this.commandArea = new CommandArea();
        this.findWordArea = new JTextArea();
        this.historyArea = new HistoryArea();

        this.setSize(FRAME_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.commandArea.addInputListener(this.historyArea);

        this.setupGraphics();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void setupGraphics() {
        this.setLayout(new BorderLayout());

        this.findWordArea.setEditable(false);
        this.findWordArea.setPreferredSize(FIND_WORD_AREA_SIZE);

        final JScrollPane scrollPane = new JScrollPane(this.findWordArea);
        final TitledBorder titleFindWordArea = BorderFactory.createTitledBorder(TITLE_FIND_WORD_AREA);
        titleFindWordArea.setTitleJustification(TitledBorder.CENTER);
        scrollPane.setBorder(titleFindWordArea);

        this.add(BorderLayout.NORTH, this.commandArea);
        this.add(BorderLayout.EAST, this.historyArea);
        this.add(BorderLayout.CENTER, scrollPane);
    }

    @Override
    public void addInputListener(final CommandListener commandListener) {
        this.commandArea.addInputListener(commandListener);
    }

    @Override
    public void onResponse(final Searcher filter) {
        SwingUtilities.invokeLater(() -> {
            this.findWordArea.setForeground(Color.BLACK);
            final String row = "Depth[%d] %s: %s=%d \n".formatted(filter.currentDepth(), filter.url(), filter.word(), filter.countWord());
            this.findWordArea.append(row);
        });
    }

    @Override
    public void onError(final String message) {
        SwingUtilities.invokeLater(() -> MessageUtils.createError(this, "ERROR: %s".formatted(message)));
    }

}
