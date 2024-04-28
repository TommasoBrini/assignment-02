package ex2.gui;

import javax.swing.*;
import java.awt.*;

public class BoxText extends JPanel {
    private final JLabel label;
    private final JTextField textField;

    public BoxText(final String text, final int columnsText) {
        this.label = new JLabel(text);
        this.textField = new JTextField(columnsText);

        final FlowLayout layout = new FlowLayout();
        this.setLayout(layout);
        this.add(this.label);
        this.add(this.textField);
    }
}
