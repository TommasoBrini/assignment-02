package ex1.inspector;

import ex1.simulation.AbstractSimulation;
import ex1.simulation.InspectorSimulation;
import ex1.simulation.SimulationSingleton;
import ex1.trafficexamples.TrafficSimulationSingleRoadSeveralCars;
import ex1.trafficexamples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import ex1.trafficexamples.TrafficSimulationWithCrossRoads;
import utils.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

enum SimulationType {
    SINGLE_ROAD("Single Road"),
    SINGLE_ROAD_TRAFFIC_LIGHT("Single Road Traffic Light"),
    CROSSROAD_TRAFFIC_LIGHT("Crossroad Traffic Light");

    private final String name;

    SimulationType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

public class SimulationView extends JPanel implements StartStopViewListener {
    private final DefaultComboBoxModel<String> comboBoxModel;
    private final JComboBox<String> comboBox;

    public SimulationView() {
        this.comboBoxModel = new DefaultComboBoxModel<>();
        this.comboBoxModel.addElement(SimulationType.SINGLE_ROAD.getName());
        this.comboBoxModel.addElement(SimulationType.SINGLE_ROAD_TRAFFIC_LIGHT.getName());
        this.comboBoxModel.addElement(SimulationType.CROSSROAD_TRAFFIC_LIGHT.getName());
        this.comboBox = new JComboBox<>(this.comboBoxModel);

        this.setLayout(new FlowLayout());
        this.add(this.comboBox);

        this.setBackground(ViewUtils.GUI_BACKGROUND_COLOR);
        this.setOpaque(false);

        this.comboBox.addActionListener(this.comboBoxActionListener);
    }

    private SimulationType simulationType() {
        final String selectedOption = (String) this.comboBox.getSelectedItem();
        if (Objects.equals(selectedOption, SimulationType.SINGLE_ROAD.getName())) {
            return SimulationType.SINGLE_ROAD;
        } else if (Objects.equals(selectedOption, SimulationType.SINGLE_ROAD_TRAFFIC_LIGHT.getName())) {
            return SimulationType.SINGLE_ROAD_TRAFFIC_LIGHT;
        } else if (Objects.equals(selectedOption, SimulationType.CROSSROAD_TRAFFIC_LIGHT.getName())) {
            return SimulationType.CROSSROAD_TRAFFIC_LIGHT;
        } else {
            throw new IllegalStateException("Simulation type not found");
        }
    }


    private final ActionListener comboBoxActionListener = e -> {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedOption = (String) comboBox.getSelectedItem();
        System.out.println("Opzione selezionata: " + selectedOption);

        SimulationSingleton.simulation = this.createSimulation();
        SimulationSingleton.simulation.addViewListener(SimulationSingleton.simulationView);
        SimulationSingleton.simulationView.setupCommandsSimulation(SimulationSingleton.simulation);
    };

    private AbstractSimulation createSimulation() {
        return switch (this.simulationType()) {
            case SINGLE_ROAD -> new TrafficSimulationSingleRoadSeveralCars();
            case SINGLE_ROAD_TRAFFIC_LIGHT -> new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
            case CROSSROAD_TRAFFIC_LIGHT -> new TrafficSimulationWithCrossRoads();
        };
    }


    @Override
    public boolean conditionToStart(final InspectorSimulation simulation) {
        return true;
    }

    @Override
    public void onStart(final InspectorSimulation simulation) {

    }

    @Override
    public void reset(final InspectorSimulation simulation) {
//        SimulationSingleton.simulation = this.createSimulation();
//        SimulationSingleton.simulation.setup();
//        SimulationSingleton.simulation.addViewListener(SimulationSingleton.simulationView);
//        SimulationSingleton.simulationView.setupCommandsSimulation(SimulationSingleton.simulation);
    }
}
