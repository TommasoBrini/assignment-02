package ex1.inspector;

import ex1.simulation.InspectorSimulation;
import utils.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartStopView extends JPanel {
    private static final String START = "Start";
    private static final String PAUSE = "Pause";
    public static final String RESET = "Reset";
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton resetButton;
    private final FlowLayout layoutManager;
    private final List<StartStopViewListener> listeners;
    private boolean isSetup;

    public StartStopView() {
        this.startButton = new JButton(START);
        this.pauseButton = new JButton(PAUSE);
        this.resetButton = new JButton(RESET);
        this.layoutManager = new FlowLayout(FlowLayout.CENTER);
        this.listeners = new ArrayList<>();

        this.graphicsSetup();
        this.setLayout(this.layoutManager);
        this.setBackground(ViewUtils.GUI_BACKGROUND_COLOR);
        this.add(this.startButton);
        this.add(this.pauseButton);
        this.add(this.resetButton);
        this.activateStartButton();

        this.isSetup = false;
        this.resetButton.setVisible(false);
    }

    private void activateStartButton() {
        this.startButton.setEnabled(true);
    }

    private void deactivateStartButton() {
        this.startButton.setEnabled(false);
    }

    private void activateStopButton() {
        this.pauseButton.setEnabled(true);
    }

    private void deactivateStopButton() {
        this.pauseButton.setEnabled(false);
    }

    private void graphicsSetup() {
        this.deactivateStartButton();
        this.deactivateStopButton();
        this.startButton.setBackground(Color.green);
        this.pauseButton.setBackground(Color.red);
    }

    private void switchStop() {
        this.deactivateStartButton();
        this.activateStopButton();
    }

    private void switchStart() {
        this.deactivateStopButton();
        this.activateStartButton();
    }

    public void addListener(final StartStopViewListener listener) {
        this.listeners.add(listener);
    }

    public void setupSimulation(final InspectorSimulation simulation) {
        this.startButton.addActionListener(e -> {
            if (!this.isSetup) {
                this.isSetup = true;
                if (this.listeners.stream().map(listener -> listener.conditionToStart(simulation)).toList().contains(false)) return;
                this.listeners.forEach(listener -> listener.onStart(simulation));
                simulation.setup();
            }
            simulation.startStopMonitor().play();
            System.out.println("PLAY");
            this.switchStop();
        });
        this.pauseButton.addActionListener(e -> {
            simulation.startStopMonitor().pause();
            this.switchStart();
        });
//        this.resetButton.addActionListener(e -> {
//            this.isSetup = false;
//            this.listeners.forEach(listener -> listener.reset(simulation));
//            this.switchStart();
//            this.resetButton.setVisible(false);
//        });
    }

    public void onEndSimulation() {
//        this.startButton.setEnabled(false);
//        this.pauseButton.setEnabled(false);
//        this.resetButton.setVisible(true);
    }
}
