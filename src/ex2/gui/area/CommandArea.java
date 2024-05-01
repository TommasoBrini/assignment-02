package ex2.gui.area;

import ex2.utils.UrlUtils;
import ex2.listener.InputGuiListener;
import ex2.utils.MessageDialogUtils;
import ex2.utils.PanelUtils;
import ex2.gui.components.TextBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    private final TextBox boxSite;
    private final TextBox boxWord;
    private final TextBox boxDepth;

    private final JButton searchButton;
    private final JButton exitButton;
    private final JButton clearButton;

    private final List<InputGuiListener> inputGuiListeners;

    public CommandArea() {
        super(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.boxSite = new TextBox(URL, SITE_COLUMNS_TEXT);
        this.boxWord = new TextBox(WORD, WORD_COLUMNS_TEXT);
        this.boxDepth = new TextBox(DEPTH, DEPTH_COLUMNS_TEXT);

        this.searchButton = new JButton(SEARCH);
        this.exitButton = new JButton(EXIT);
        this.clearButton = new JButton(CLEAR);

        this.inputGuiListeners = new ArrayList<>();

        final JPanel northPanel = PanelUtils.createPanelWithFlowLayout();
        northPanel.add(this.boxSite);
        final JPanel centerPanel = PanelUtils.createPanelWithFlowLayout();
        centerPanel.add(this.boxWord);
        centerPanel.add(this.boxDepth);
        final JPanel southPanel = PanelUtils.createPanelWithFlowLayout();
        southPanel.add(this.searchButton);
        southPanel.add(this.clearButton);
        southPanel.add(this.exitButton);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        this.setupListener();
    }

    private void setupListener() {
        this.searchButton.addActionListener(l -> {
            if (this.isInputValid()) {
                this.inputGuiListeners.forEach(listener -> listener.onSearch(
                        this,
                        this.boxSite.getText(),
                        this.boxWord.getText(),
                        Integer.parseInt(this.boxDepth.getText())));

            } else {
                MessageDialogUtils.createError(this, "Invalid input");
            }
        });
        this.exitButton.addActionListener(l -> this.inputGuiListeners.forEach(InputGuiListener::onExit));
        this.clearButton.addActionListener(l -> this.clearText());
    }

    public void addInputListener(final InputGuiListener commandListener) {
        if (Objects.nonNull(commandListener)) this.inputGuiListeners.add(commandListener);
    }

    public void setSiteBoxText(final String text) {
        this.boxSite.setText(text);
    }

    public void setWordBoxText(final String word) {
        this.boxWord.setText(word);
    }

    public void setDepthBoxText(final String depth) {
        this.boxDepth.setText(depth);
    }

    private boolean isSiteValid() {
        final String url = this.boxSite.getText();
        return !url.isBlank() && UrlUtils.isValidURL(url);
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
        return this.isSiteValid() && this.isWordValid() && this.isDepthValid();
    }

    private void clearText() {
        this.boxSite.clear();
        this.boxWord.clear();
        this.boxDepth.clear();
    }
}
