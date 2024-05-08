package ex2.gui.components;

import javax.swing.*;
import java.awt.*;

public class TextBox extends JPanel {
    private final JLabel label;
    private final JTextField textField;

    public TextBox(final String text, final int columnsText) {
        this.label = new JLabel(text);
        this.textField = new JTextField(columnsText);

        final FlowLayout layout = new FlowLayout();
        this.setBackground(Color.WHITE);
        this.setLayout(layout);
        this.add(this.label);
        this.add(this.textField);
    }

    public String getText() {
        return this.textField.getText();
    }

    public void clear() {
        this.textField.setText("");
    }

    public void setText(final String text) {
        this.textField.setText(text);
    }
}
