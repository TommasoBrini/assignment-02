package ex2.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HistoryArea extends JPanel {
    private static final String SITE = "Site";
    private static final String WORD = "Word";
    private static final String DEPTH = "Depth";
    private static final String CLEAR = "Clear";
    private static final Dimension SITE_AREA_SIZE = new Dimension(200, 350);
    private static final Dimension WORD_AREA_SIZE = new Dimension(50, 350);
    private static final Dimension DEPTH_AREA_SIZE = new Dimension(50, 350);

    private final JTextArea siteArea;
    private final JTextArea wordArea;
    private final JTextArea depthArea;
    private final JButton clearButton;

    public HistoryArea() {
        this.siteArea = new JTextArea();
        this.wordArea = new JTextArea();
        this.depthArea = new JTextArea();
        this.clearButton = new JButton(CLEAR);

        this.siteArea.setEditable(false);
        this.wordArea.setEditable(false);
        this.depthArea.setEditable(false);

        this.siteArea.setPreferredSize(SITE_AREA_SIZE);
        this.wordArea.setPreferredSize(WORD_AREA_SIZE);
        this.depthArea.setPreferredSize(DEPTH_AREA_SIZE);
        this.clearButton.setPreferredSize(new Dimension(200, 30));

        final JScrollPane siteScrollPane = new JScrollPane(this.siteArea);
        final JScrollPane wordScrollPane = new JScrollPane(this.wordArea);
        final JScrollPane depthScrollPane = new JScrollPane(this.depthArea);

        final TitledBorder titleSite = BorderFactory.createTitledBorder(SITE);
        titleSite.setTitleJustification(TitledBorder.CENTER);
        siteScrollPane.setBorder(titleSite);

        final TitledBorder titleWord = BorderFactory.createTitledBorder(WORD);
        titleWord.setTitleJustification(TitledBorder.CENTER);
        wordScrollPane.setBorder(titleWord);

        final TitledBorder titleDepth = BorderFactory.createTitledBorder(DEPTH);
        titleDepth.setTitleJustification(TitledBorder.CENTER);
        depthScrollPane.setBorder(titleDepth);

        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        final JPanel areaPanel = new JPanel();
        areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.X_AXIS));
        areaPanel.setBackground(Color.WHITE);
        areaPanel.add(siteScrollPane);
        areaPanel.add(wordScrollPane);
        areaPanel.add(depthScrollPane);
        this.add(areaPanel, BorderLayout.CENTER);
        this.add(this.clearButton, BorderLayout.SOUTH);

        this.setupListener();
    }

    private void setupListener() {
        this.clearButton.addActionListener(l -> this.clear());
    }

    private void clear() {
        this.siteArea.setText("");
        this.wordArea.setText("");
        this.depthArea.setText("");
    }

    public void append(final String site, final String word, final String depth) {
        this.siteArea.append(site + "\n");
        this.wordArea.append(word + "\n");
        this.depthArea.append(depth + "\n");
    }
}
