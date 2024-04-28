package ex2.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchWord extends JFrame {
    private static final Dimension FIND_WORD_AREA_SIZE = new Dimension(430, 380);
    private static final Dimension FRAME_SIZE = new Dimension(800, 500);
    private static final String TITLE_FIND_WORD_AREA = "Find Word";
    private static final String SITE = "Site:";
    private static final String WORD = "Word:";
    private static final String DEPTH = "Depth:";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String CLEAR = "Clear";
    private static final int SITE_COLUMNS_TEXT = 15;
    private static final int WORD_COLUMNS_TEXT = 5;
    private static final int DEPTH_COLUMNS_TEXT = 3;

    private final TextBox boxSite;
    private final TextBox boxWord;
    private final TextBox boxDepth;
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton clearButton;
    private final JTextArea FindWordArea;
    private final HistoryArea historyArea;

    public SearchWord() {
        super("Search Word");
        this.setSize(FRAME_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.boxSite = new TextBox(SITE, SITE_COLUMNS_TEXT);
        this.boxWord = new TextBox(WORD, WORD_COLUMNS_TEXT);
        this.boxDepth = new TextBox(DEPTH, DEPTH_COLUMNS_TEXT);
        this.startButton = new JButton(START);
        this.stopButton = new JButton(STOP);
        this.clearButton = new JButton(CLEAR);
        this.FindWordArea = new JTextArea();
        this.historyArea = new HistoryArea();

        this.setupGraphics();
        this.setupListener();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void setupGraphics() {
        this.setLayout(new BorderLayout());

        this.FindWordArea.setEditable(false);
        this.FindWordArea.setPreferredSize(FIND_WORD_AREA_SIZE);

        final JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.add(this.boxSite);
        northPanel.add(this.boxWord);
        northPanel.add(this.boxDepth);
        northPanel.add(this.startButton);
        northPanel.add(this.stopButton);
        northPanel.add(this.clearButton);

        final JScrollPane scrollPane = new JScrollPane(this.FindWordArea);
        final TitledBorder titleDepth = BorderFactory.createTitledBorder(TITLE_FIND_WORD_AREA);
        titleDepth.setTitleJustification(TitledBorder.CENTER);
        scrollPane.setBorder(titleDepth);

        this.add(BorderLayout.NORTH, northPanel);
        this.add(BorderLayout.EAST, this.historyArea);
        this.add(BorderLayout.CENTER, scrollPane);
    }

    private void setupListener() {
        this.startButton.addActionListener(l -> this.appendSiteInHistory());
        this.clearButton.addActionListener(l -> this.clearText());
    }

    private void clearText() {
        this.boxSite.clear();
        this.boxWord.clear();
        this.boxDepth.clear();
    }

    private void appendSiteInHistory() {
        final String site = this.boxSite.getText();
        final String word = this.boxWord.getText();
        final String depth = this.boxDepth.getText();
        if (site.isEmpty() || word.isEmpty() || depth.isEmpty()) {
            MessageUtils.createError(this, "All fields must be filled");
            return;
        }
        this.historyArea.append(this.boxSite.getText(), this.boxWord.getText(), this.boxDepth.getText());
    }


    public static void main(final String[] args) {
        new SearchWord();
    }

}
