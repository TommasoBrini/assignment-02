package ex2.gui.area;

import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.InputGuiListener;
import ex2.utils.PanelUtils;
import ex2.worker.concrete.WorkerStrategy;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static ex2.gui.GuiConstants.*;

public class HistoryArea extends JPanel implements InputGuiListener {
    private static final String HISTORY = "History";
    private static final String CLEAR = "Clear";
    private static final int INDEX_ADD_BUTTON_IN_PANEL = 0;

    private final CommandArea commandArea;
    private final JPanel recordPanel;
    private final JButton clearButton;

    public HistoryArea(final CommandArea commandArea) {
        this.commandArea = commandArea;
        this.clearButton = new JButton(CLEAR);
        this.recordPanel = new JPanel();

        this.setupGraphics();
        this.setupListener();
    }

    private void setupGraphics() {
        final JScrollPane scrollPane = new JScrollPane(this.recordPanel);
        this.recordPanel.setLayout(new BoxLayout(this.recordPanel, BoxLayout.Y_AXIS));

        final TitledBorder titleRecord = BorderFactory.createTitledBorder(HISTORY);
        titleRecord.setTitleJustification(TitledBorder.CENTER);
        scrollPane.setBorder(titleRecord);

        scrollPane.setPreferredSize(HISTORY_AREA_DIMENSION);

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

    public void append(final String site, final String word, final String depth) {
        final JButton button = new JButton();
        button.setMaximumSize(new Dimension(HISTORY_AREA_DIMENSION.width, HISTORY_BUTTON_HEIGHT));

        final JPanel mainPanel = PanelUtils.createPanelWithBorderLayout();
        final JPanel southPanel = PanelUtils.createPanelWithBorderLayout();
        final JPanel panel = PanelUtils.createPanelWithFlowLayout(FlowLayout.CENTER, 20, INDEX_ADD_BUTTON_IN_PANEL);

        panel.add(new JLabel(WORD + word));
        panel.add(new JLabel(DEPTH + depth));
        southPanel.add(panel, BorderLayout.CENTER);

        mainPanel.setMaximumSize(new Dimension(HISTORY_AREA_DIMENSION.width, HISTORY_BUTTON_HEIGHT));
        mainPanel.add(new JLabel(URL + site), BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        button.add(mainPanel);

        button.addActionListener(l -> {
            this.commandArea.setSiteBoxText(site);
            this.commandArea.setWordBoxText(word);
            this.commandArea.setDepthBoxText(depth);
        });

        this.recordPanel.add(button, INDEX_ADD_BUTTON_IN_PANEL);
        this.recordPanel.revalidate();
        this.recordPanel.repaint();
    }

    @Override
    public void onSearch(final WorkerStrategy workerStrategy, final SearcherType searcherType, final String site, final String word, final int maxDepth) {
        this.append(site, word, String.valueOf(maxDepth));
    }

    @Override
    public void onExit() {

    }
}
