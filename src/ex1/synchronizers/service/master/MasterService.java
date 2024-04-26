package ex1.synchronizers.service.master;

import ex1.car.CarAgent;

public interface MasterService {
    void setup();

    void terminateWorkers();

    void execute();

    void breakBarrierAction();

    void addCarAgent(final CarAgent carAgent);
}
