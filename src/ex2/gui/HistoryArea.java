package ex2.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HistoryArea extends JPanel {
    private static final String SITE = "Site";
    private static final String WORD = "Word";
    private static final String DEPTH = "Depth";
    private static final String CLEAR = "Clear";
    private static final Dimension AREA_SIZE = new Dimension(300, 350);

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

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(this.siteArea);
        panel.add(this.wordArea);
        panel.add(this.depthArea);

        final JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(AREA_SIZE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        final TitledBorder titleSite = BorderFactory.createTitledBorder(SITE);
        titleSite.setTitleJustification(TitledBorder.CENTER);
        this.siteArea.setBorder(titleSite);

        final TitledBorder titleWord = BorderFactory.createTitledBorder(WORD);
        titleWord.setTitleJustification(TitledBorder.CENTER);
        this.wordArea.setBorder(titleWord);

        final TitledBorder titleDepth = BorderFactory.createTitledBorder(DEPTH);
        titleDepth.setTitleJustification(TitledBorder.CENTER);
        this.depthArea.setBorder(titleDepth);


        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
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
