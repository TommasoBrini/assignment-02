package ex1.simulation;

import ex1.trafficexamples.TrafficSimulationSingleRoadSeveralCars;
import ex1.trafficexamples.TrafficSimulationSingleRoadSeveralCars;

public class SimulationSingleton {

    public static AbstractSimulation simulation = new TrafficSimulationSingleRoadSeveralCars();
    public static SimulationView simulationView = new SimulationView();
}
