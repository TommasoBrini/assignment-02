package ex2.gui;

import javax.swing.*;
import java.awt.*;

public final class PanelUtils {

    public static JPanel createPanelWithFlowLayout(final int align, final int hgap, final int vgap) {
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(align, hgap, vgap));
        panel.setOpaque(false);
        return panel;
    }

    public static JPanel createPanelWithFlowLayout() {
        return createPanelWithFlowLayout(FlowLayout.CENTER, 0, 0);
    }

    public static JPanel createPanelWithBorderLayout() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        return panel;
    }
}
