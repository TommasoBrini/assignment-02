package ex2.gui.area;

import ex2.eventLoop.CommandListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class HistoryArea extends JPanel implements CommandListener {
    private static final String SITE = "Site";
    private static final String WORD = "Word";
    private static final String DEPTH = "Depth";
    private static final String SPACE = "     ";
    private static final String CLEAR = "Clear";
    private static final Dimension AREA_SIZE = new Dimension(500, 450);
    private static final int BUTTON_HEIGHT = 30;

    private final JPanel recordPanel;
    private final JButton clearButton;

    public HistoryArea() {
        this.clearButton = new JButton(CLEAR);
        this.recordPanel = new JPanel();

        this.setupGraphics();
        this.setupListener();
    }

    private void setupGraphics() {
        final JScrollPane scrollPane = new JScrollPane(this.recordPanel);
        this.recordPanel.setLayout(new BoxLayout(this.recordPanel, BoxLayout.Y_AXIS));

        final TitledBorder titleRecord = BorderFactory.createTitledBorder(SITE + SPACE + WORD + SPACE + DEPTH);
        titleRecord.setTitleJustification(TitledBorder.CENTER);
        scrollPane.setBorder(titleRecord);

        scrollPane.setPreferredSize(AREA_SIZE);

        this.recordPanel.setBackground(Color.WHITE);
        this.setBackground(Color.WHITE);

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(this.clearButton, BorderLayout.SOUTH);
    }

    private void setupListener() {
        this.clearButton.addActionListener(l -> this.clear());
    }

    private void clear() {
        this.recordPanel.removeAll();
        this.recordPanel.revalidate();
        this.recordPanel.repaint();
    }

    public void append(final CommandArea commandArea, final String site, final String word, final String depth) {
        final JButton button = new JButton();
        button.setMaximumSize(new Dimension(AREA_SIZE.width, BUTTON_HEIGHT));

        final JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setMaximumSize(new Dimension(AREA_SIZE.width, BUTTON_HEIGHT));
        panel.setOpaque(false);
        panel.add(new JLabel(site, SwingConstants.LEFT));
        panel.add(new JLabel(word, SwingConstants.CENTER));
        panel.add(new JLabel(depth, SwingConstants.RIGHT));

        button.add(panel);

        button.addActionListener(l -> {
            commandArea.setSiteBoxText(site);
            commandArea.setWordBoxText(word);
            commandArea.setDepthBoxText(depth);
        });

        this.recordPanel.add(button);
        this.recordPanel.revalidate();
        this.recordPanel.repaint();
    }

    @Override
    public void onSearch(final CommandArea commandArea, final String site, final String word, final int depth) {
        this.append(commandArea, site, word, String.valueOf(depth));
    }

    @Override
    public void onStop() {

    }
}
