package ex2.utils;

import javax.swing.*;
import java.util.List;

public final class ComboBoxUtils {


    public static <E extends Enum<E>> JComboBox<E> createComboBox(final List<E> enums) {
        final DefaultComboBoxModel<E> comboBoxModel = new DefaultComboBoxModel<>();
        enums.forEach(comboBoxModel::addElement);
        return new JComboBox<>(comboBoxModel);
    }

}
