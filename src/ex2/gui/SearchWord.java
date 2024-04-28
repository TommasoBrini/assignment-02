package ex2.gui;

import javax.swing.*;
import java.awt.*;

public class SearchWord extends JFrame {
    private final BoxText boxSite;
    private final BoxText boxWord;
    private final BoxText boxDepth;
    private final JButton startButton;
    private final JButton stopButton;
    private final JTextArea textArea;

    public SearchWord() {
        super("Search Word");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.boxSite = new BoxText("Site:", 15);
        this.boxWord = new BoxText("Word:", 5);
        this.boxDepth = new BoxText("Depth:", 3);
        this.startButton = new JButton("Start");
        this.stopButton = new JButton("Stop");
        this.textArea = new JTextArea();

        this.textArea.setEnabled(false);
        this.setLayout(new BorderLayout());

        final JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.add(this.boxSite);
        northPanel.add(this.boxWord);
        northPanel.add(this.boxDepth);
        northPanel.add(this.startButton);
        northPanel.add(this.stopButton);

        this.add(BorderLayout.NORTH, northPanel);
        this.add(BorderLayout.CENTER, new JScrollPane(this.textArea));
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(final String[] args) {
        new SearchWord();
    }

}
