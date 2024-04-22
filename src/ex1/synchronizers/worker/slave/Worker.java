package ex1.synchronizers.worker.slave;

import ex1.car.command.CarCommand;

public interface Worker {

    void pause();

    void setCarCommand(CarCommand command);

    void terminate();

}
