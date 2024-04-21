package ex1.inspector;

import ex1.simulation.InspectorSimulation;
import ex1.synchronizers.worker.master.MultiWorkerGeneric;
import ex1.synchronizers.worker.master.MultiWorkerSpecific;
import utils.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

enum WorkerType {
    GENERIC("Generic Worker"),
    SPECIFIC("Specific Worker");

    private final String name;

    WorkerType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

public class MasterWorkerView extends JPanel implements StartStopViewListener {
    private final DefaultComboBoxModel<String> comboBoxModel;
    private final JComboBox<String> comboBox;

    // Generic
    private final JPanel genericPanel;
    private final JLabel genericLabel;
    private final JTextField genericTextField;
    // Specific
    private final JPanel specificPanel;
    private final JLabel senseLabel;
    private final JTextField senseTextField;
    private final JLabel decideLabel;
    private final JTextField decideTextField;
    private final JLabel actionLabel;
    private final JTextField actionTextField;

    // Label Error


    public MasterWorkerView() {
        this.comboBoxModel = new DefaultComboBoxModel<>();
        this.comboBoxModel.addElement(WorkerType.GENERIC.getName());
        this.comboBoxModel.addElement(WorkerType.SPECIFIC.getName());
        this.comboBox = new JComboBox<>(this.comboBoxModel);

        // Generic
        this.genericLabel = new JLabel(WorkerType.GENERIC.getName());
        this.genericTextField = new JTextField("2", 3);
        // Specific
        this.senseLabel = new JLabel("Sense");
        this.senseTextField = new JTextField("2", 3);
        this.decideLabel = new JLabel("Decide");
        this.decideTextField = new JTextField("2", 3);
        this.actionLabel = new JLabel("Action");
        this.actionTextField = new JTextField("2", 3);

        // Generic setup
        this.genericPanel = new JPanel(new FlowLayout());
        this.genericPanel.add(this.genericLabel);
        this.genericPanel.add(this.genericTextField);

        // Specific setup
        this.specificPanel = new JPanel(new FlowLayout());
        this.specificPanel.add(this.senseLabel);
        this.specificPanel.add(this.senseTextField);
        this.specificPanel.add(this.decideLabel);
        this.specificPanel.add(this.decideTextField);
        this.specificPanel.add(this.actionLabel);
        this.specificPanel.add(this.actionTextField);

        this.setLayout(new FlowLayout());
        this.add(this.genericPanel);
        this.add(this.specificPanel);
        this.add(this.comboBox);

        this.specificPanel.setVisible(false);

        this.setBackground(ViewUtils.GUI_BACKGROUND_COLOR);
        this.setOpaque(false);

        this.comboBox.addActionListener(this.comboBoxActionListener);
    }

    @Override
    public boolean conditionToStart(final InspectorSimulation simulation) {
        return this.checkValue();
    }

    @Override
    public void onStart(final InspectorSimulation simulation) {
        if (!this.checkValue()) return;
        this.setupSimulation(simulation);
    }

    @Override
    public void reset(final InspectorSimulation simulation) {

    }

    private void setupSimulation(final InspectorSimulation simulation) {
        switch (this.workerType()) {
            case GENERIC:
                    simulation.setMasterWorker(
                            new MultiWorkerGeneric(simulation.startStopMonitor(),
                                    this.getGenericWorker()));
                break;
            case SPECIFIC:
                    simulation.setMasterWorker(
                            new MultiWorkerSpecific(simulation.startStopMonitor(),
                                    this.getSense(),
                                    this.getDecide(),
                                    this.getAction()));
                break;
        }
    }

    private boolean checkValue() {
        return this.getGenericWorker() > 0 && this.getSense() > 0 && this.getDecide() > 0 && this.getAction() > 0;
    }

    private final ActionListener comboBoxActionListener = e -> {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedOption = (String) comboBox.getSelectedItem();
        System.out.println("Opzione selezionata: " + selectedOption);

        if (Objects.equals(selectedOption, WorkerType.GENERIC.getName())) {
            MasterWorkerView.this.genericPanel.setVisible(true);
            MasterWorkerView.this.specificPanel.setVisible(false);
        } else {
            MasterWorkerView.this.genericPanel.setVisible(false);
            MasterWorkerView.this.specificPanel.setVisible(true);
        }
    };

    private int getGenericWorker() {
        try {
            return Integer.parseInt(this.genericTextField.getText());
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    private int getSense() {
        try {
            return Integer.parseInt(this.senseTextField.getText());
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    private int getDecide() {
        try {
            return Integer.parseInt(this.decideTextField.getText());
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    private int getAction() {
        try {
            return Integer.parseInt(this.actionTextField.getText());
        } catch (final NumberFormatException e) {
            return -1;
        }
    }

    private WorkerType workerType() {
        final String selectedOption = (String) this.comboBox.getSelectedItem();
        if (Objects.equals(selectedOption, WorkerType.GENERIC.getName())) {
            return WorkerType.GENERIC;
        } else {
            return WorkerType.SPECIFIC;
        }
    }


}
