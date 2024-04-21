package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMasterWorker {
    private final StartStopMonitor starStopMonitorSimulation;
    private final List<CarAgent> carAgents;

    public BaseMasterWorker(final StartStopMonitor starStopMonitorSimulation) {
        this.starStopMonitorSimulation = starStopMonitorSimulation;
        this.carAgents = new ArrayList<>();
    }

    protected StartStopMonitor startStopMonitorSimulation() {
        return this.starStopMonitorSimulation;
    }

    protected List<CarAgent> carAgents() {
        return this.carAgents;
    }

    protected void setDtToCarAgents(final int dt) {
        this.carAgents.stream().parallel().forEach(carAgent -> carAgent.setTimeDt(dt));
    }

    public void addCarAgent(final CarAgent carAgent) {
        this.carAgents.add(carAgent);
    }

}
