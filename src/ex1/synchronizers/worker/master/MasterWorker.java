package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;

import java.util.List;
import java.util.concurrent.Future;

public interface MasterWorker {

    void setup();

    void execute(int dt);

    void terminateWorkers();

    void addCarAgent(final CarAgent carAgent);

    boolean hasCommands();

    List<? extends Future<?>> callNextTaskCommand();

    void startSimulation();
}
