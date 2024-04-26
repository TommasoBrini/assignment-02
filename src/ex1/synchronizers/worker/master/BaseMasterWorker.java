package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;
import ex1.synchronizers.worker.slave.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class BaseMasterWorker {
    private final StartStopMonitor starStopMonitorSimulation;
    private final List<CarAgent> carAgents;
    protected final ExecutorService executor;

    public BaseMasterWorker() {
        this.starStopMonitorSimulation = new StartStopMonitorImpl();
        this.carAgents = new ArrayList<>();
        this.executor = Executors.newSingleThreadExecutor();
        // TODO: varie strategie da cambiare
//        Executors.newFixedThreadPool(6);
    }

    public List<? extends Future<?>> runTask(final List<Worker> workers) {
        return workers.stream().map(this.executor::submit).toList();
//        workers.forEach(this.executor::submit);
    }

    protected StartStopMonitor startStopMonitorSimulation() {
        return this.starStopMonitorSimulation;
    }

    protected List<CarAgent> carAgents() {
        return this.carAgents;
    }

    protected void setDtToCarAgents(final int dt) {
        this.carAgents.forEach(carAgent -> carAgent.setTimeDt(dt));
    }

    public void addCarAgent(final CarAgent carAgent) {
        this.carAgents.add(carAgent);
    }

    public void startSimulation() {
        this.starStopMonitorSimulation.play();
    }

}
