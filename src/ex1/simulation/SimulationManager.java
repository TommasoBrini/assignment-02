package ex1.simulation;

import ex1.trafficexamples.TrafficSimulationSingleRoadSeveralCars;

public class SimulationManager {
    private AbstractSimulation simulation;
    private final SimulationView view;

    public SimulationManager() {
        this.view = new SimulationView(this);
        this.simulation = new TrafficSimulationSingleRoadSeveralCars();
    }

    public void setSimulation(final AbstractSimulation simulation) {
        this.simulation = simulation;
        this.simulation.addViewListener(this.view);
        this.view.setupCommandsSimulation(this.simulation);
    }


}
