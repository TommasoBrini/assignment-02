package ex2.gui;

import javax.swing.*;

public final class MessageUtils {

    public static void createError(final JFrame frame, final String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
