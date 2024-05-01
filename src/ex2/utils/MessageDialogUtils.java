package ex2.utils;

import javax.swing.*;
import java.awt.*;

public final class MessageDialogUtils {

    public static void createError(final Component component, final String message) {
        JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
