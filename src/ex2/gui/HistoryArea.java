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

//    private final JTextArea siteArea;
//    private final JTextArea wordArea;
//    private final JTextArea depthArea;
//    private final JPanel buttonPanel;
    private final JPanel recordPanel;
    private final JButton clearButton;

    public HistoryArea() {
        this.clearButton = new JButton(CLEAR);

        this.recordPanel = new JPanel();
        final JScrollPane scrollPane = new JScrollPane(this.recordPanel);
        this.recordPanel.setLayout(new BoxLayout(this.recordPanel, BoxLayout.Y_AXIS));

        scrollPane.setPreferredSize(AREA_SIZE);


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
        this.recordPanel.removeAll();
        this.recordPanel.revalidate();
        this.recordPanel.repaint();
    }

    public void append(final String site, final String word, final String depth) {
        final JButton button = new JButton();
        button.setMaximumSize(new Dimension(AREA_SIZE.width, button.getPreferredSize().height)); // Imposta larghezza massima

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(site), BorderLayout.WEST);
        panel.add(new JLabel(word), BorderLayout.CENTER);
        panel.add(new JLabel(depth), BorderLayout.EAST);

        button.add(panel);

        this.recordPanel.add(button);
        this.recordPanel.revalidate();
        this.recordPanel.repaint();
    }
}
