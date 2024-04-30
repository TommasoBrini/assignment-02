package ex2.gui;

import javax.swing.*;
import java.awt.*;

public final class MessageUtils {

    public static void createError(final Component component, final String message) {
        JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
