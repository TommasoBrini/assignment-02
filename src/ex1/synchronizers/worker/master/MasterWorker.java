package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;

public interface MasterWorker {

    void setup();

    void execute(int dt);

    void terminateWorkers();

    void addCarAgent(final CarAgent carAgent);
}
