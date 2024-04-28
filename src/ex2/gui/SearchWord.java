package ex2.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchWord extends JFrame {
    private static final Dimension FIND_WORD_AREA_SIZE = new Dimension(430, 380);
    private static final Dimension FRAME_SIZE = new Dimension(800, 500);
    private static final String TITLE_FIND_WORD_AREA = "Find Word";
    private final TextBox boxSite;
    private final TextBox boxWord;
    private final TextBox boxDepth;
    private final JButton startButton;
    private final JButton stopButton;
    private final JTextArea FindWordArea;
    private final HistoryArea historyArea;

    public SearchWord() {
        super("Search Word");
        this.setSize(FRAME_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.boxSite = new TextBox("Site:", 15);
        this.boxWord = new TextBox("Word:", 5);
        this.boxDepth = new TextBox("Depth:", 3);
        this.startButton = new JButton("Start");
        this.stopButton = new JButton("Stop");
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
