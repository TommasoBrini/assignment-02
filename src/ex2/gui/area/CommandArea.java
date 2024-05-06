package ex2.gui.area;

import ex2.core.component.DataEvent;
import ex2.core.component.concrete.DataEventImpl;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.InputGuiListener;
import ex2.gui.GUIAnalysis;
import ex2.gui.components.TextBox;
import ex2.server.Server;
import ex2.utils.ComboBoxUtils;
import ex2.utils.MessageDialogUtils;
import ex2.utils.PanelUtils;
import ex2.utils.UrlUtils;
import ex2.worker.concrete.WorkerStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ex2.gui.GuiConstants.*;

public class CommandArea extends JPanel {
    private static final String SEARCH = "Search";
    private static final String EXIT = "Exit";
    private static final String CLEAR = "Clear";

    private static final int SITE_COLUMNS_TEXT = 30;
    private static final int WORD_COLUMNS_TEXT = 15;
    private static final int DEPTH_COLUMNS_TEXT = 4;
    private static final String MAGIC = "Magic";

    private final TextBox boxUrl;
    private final TextBox boxWord;
    private final TextBox boxDepth;

    private final JButton searchButton;
    private final JButton exitButton;
    private final JButton clearButton;
    private final JButton magicButton;

    private final JComboBox<SearcherType> searcherTypeComboBox;
    private final JComboBox<WorkerStrategy> workerStrategyComboBox;

    private final List<InputGuiListener> inputGuiListeners;

    public CommandArea() {
        super(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.boxUrl = new TextBox(URL, SITE_COLUMNS_TEXT);
        this.boxWord = new TextBox(WORD, WORD_COLUMNS_TEXT);
        this.boxDepth = new TextBox(DEPTH, DEPTH_COLUMNS_TEXT);

        this.searchButton = new JButton(SEARCH);
        this.exitButton = new JButton(EXIT);
        this.clearButton = new JButton(CLEAR);
        this.magicButton = new JButton(MAGIC);

        this.searcherTypeComboBox = ComboBoxUtils.createComboBox(Arrays.stream(SearcherType.values()).toList());
        this.workerStrategyComboBox = ComboBoxUtils.createComboBox(Arrays.stream(WorkerStrategy.values()).toList());

        this.inputGuiListeners = new ArrayList<>();

        final JPanel northPanel = PanelUtils.createPanelWithFlowLayout();
        northPanel.add(this.boxUrl);
        final JPanel centerPanel = PanelUtils.createPanelWithFlowLayout();
        centerPanel.add(this.boxWord);
        centerPanel.add(this.boxDepth);
        final JPanel southPanel = PanelUtils.createPanelWithFlowLayout();
        southPanel.add(this.workerStrategyComboBox);
        southPanel.add(this.searcherTypeComboBox);
        southPanel.add(this.searchButton);
        southPanel.add(this.clearButton);
        southPanel.add(this.magicButton);
        southPanel.add(this.exitButton);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        this.setupListener();
    }

    private void setupListener() {
        this.searchButton.addActionListener(l -> {
            if (this.isInputValid()) {
                final DataEvent dataEvent = new DataEventImpl(
                        this.workerStrategy(), this.searcherType(),
                        this.boxUrl.getText(), this.boxWord.getText(), Integer.parseInt(this.boxDepth.getText()), 0);
                this.inputGuiListeners.forEach(listener -> listener.onSearch(dataEvent));
                this.disableCommand();
            } else {
                MessageDialogUtils.createError(this, "Invalid input");
            }
        });
        this.exitButton.addActionListener(l -> this.inputGuiListeners.forEach(InputGuiListener::onExit));
        this.clearButton.addActionListener(l -> this.clearText());
    }

    public void setupAnalisys(final GUIAnalysis guiAnalysis) {
        this.magicButton.addActionListener(l -> guiAnalysis.changeVisible());
    }

    public void addInputListener(final InputGuiListener commandListener) {
        if (Objects.nonNull(commandListener)) this.inputGuiListeners.add(commandListener);
    }

    public void setSiteBoxText(final String text) {
        this.boxUrl.setText(text);
    }

    public void setWordBoxText(final String word) {
        this.boxWord.setText(word);
    }

    public void setDepthBoxText(final String depth) {
        this.boxDepth.setText(depth);
    }

    public void setWorkerStrategy(final WorkerStrategy workerStrategy) {
        this.workerStrategyComboBox.setSelectedItem(workerStrategy);
    }

    public void setSearcherType(final SearcherType searcherType) {
        this.searcherTypeComboBox.setSelectedItem(searcherType);
    }

    private SearcherType searcherType() {
        return (SearcherType) this.searcherTypeComboBox.getSelectedItem();
    }

    public WorkerStrategy workerStrategy() {
        return (WorkerStrategy) this.workerStrategyComboBox.getSelectedItem();
    }

    private boolean isUrlValid() {
        final String url = this.boxUrl.getText();
        final boolean checkSearcher = !SearcherType.LOCAL.equals(this.searcherType()) || url.contains(Server.LOCAL_PATH);
        return !url.isBlank() && checkSearcher && UrlUtils.isValidURL(url);
    }

    private boolean isWordValid() {
        return !this.boxWord.getText().isBlank();
    }

    private boolean isDepthValid() {
        try {
            final String depth = this.boxDepth.getText();
            final int depthInt = Integer.parseInt(depth);
            return depthInt > 0;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private boolean isInputValid() {
        return this.isUrlValid() && this.isWordValid() && this.isDepthValid();
    }

    private void clearText() {
        this.boxUrl.clear();
        this.boxWord.clear();
        this.boxDepth.clear();
    }

    private void disableCommand() {
        this.searchButton.setEnabled(false);
        this.exitButton.setEnabled(false);
    }

    public void enableCommand() {
        this.searchButton.setEnabled(true);
        this.exitButton.setEnabled(true);
    }

}
