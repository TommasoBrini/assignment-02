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
    private static final String RESUME = "Resume";
    public static final String RESET = "Reset";
    public static final String STOP = "Stop";
    private final JButton startButton;
    private final JButton pauseResumeButton;
    private final JButton resetButton;
    private final JButton stopButton;
    private final FlowLayout layoutManager;
    private final List<StartStopViewListener> listeners;
    private boolean isSetup;

    public StartStopView() {
        this.startButton = new JButton(START);
        this.pauseResumeButton = new JButton(PAUSE);
        this.resetButton = new JButton(RESET);
        this.stopButton = new JButton(STOP);
        this.layoutManager = new FlowLayout(FlowLayout.CENTER);
        this.listeners = new ArrayList<>();

        this.graphicsSetup();
        this.setLayout(this.layoutManager);
        this.setBackground(ViewUtils.GUI_BACKGROUND_COLOR);
        this.add(this.startButton);
        this.add(this.pauseResumeButton);
        this.add(this.resetButton);
        this.add(this.stopButton);
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

    private void activatePauseButton() {
        this.pauseResumeButton.setEnabled(true);
    }

    private void deactivatePauseButton() {
        this.pauseResumeButton.setEnabled(false);
    }

    private void activateStopButton() {
        this.stopButton.setEnabled(true);
    }

    private void deactivateStopButton() {
        this.stopButton.setEnabled(false);
    }

    private void graphicsSetup() {
        this.deactivateStartButton();
        this.deactivatePauseButton();
        this.deactivateStopButton();
        this.startButton.setBackground(Color.green);
        this.pauseResumeButton.setBackground(Color.yellow);
        this.stopButton.setBackground(Color.red);
    }

    private void switchStop() {
        this.deactivateStartButton();
        this.activatePauseButton();
        this.activateStopButton();
    }


//    private void switchStart() {
//        this.deactivatePauseButton();
//        this.activateStartButton();
//    }


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
        this.pauseResumeButton.addActionListener(e -> {
            if (this.pauseResumeButton.getText().equals(PAUSE)) {
                simulation.startStopMonitor().pause();
                this.pauseResumeButton.setText(RESUME);
            } else {
                //esegue come se fosse start
                simulation.startStopMonitor().play();
                this.pauseResumeButton.setText(PAUSE);
            }
        });
//        this.resetButton.addActionListener(e -> {
//            this.isSetup = false;
//            this.listeners.forEach(listener -> listener.reset(simulation));
//            this.switchStart();
//            this.resetButton.setVisible(false);
//        });
        this.stopButton.addActionListener(e -> {
            simulation.startStopMonitor().pause();
            this.onEndSimulation();
            JOptionPane.showMessageDialog(this, "Simulation closed");
            System.exit(0);
        });
    }

    public void onEndSimulation() {
//        this.startButton.setEnabled(false);
//        this.pauseButton.setEnabled(false);
//        this.resetButton.setVisible(true);
        this.deactivatePauseButton();
        this.deactivateStartButton();
        this.deactivateStopButton();
    }
}
