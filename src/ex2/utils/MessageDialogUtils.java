package ex2.utils;

import ex2.gui.GUISearchWord;

import javax.swing.*;
import java.awt.*;

public final class MessageDialogUtils {

    public static void createError(final Component component, final String message) {
        JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void createMessage(final Component component, final String message) {
        JOptionPane.showMessageDialog(component, message, "Finish", JOptionPane.INFORMATION_MESSAGE);

    }
}
