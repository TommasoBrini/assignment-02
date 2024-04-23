package ex1.synchronizers.worker.slave;

import ex1.car.command.CarCommand;

public interface Worker {

    void setCarCommand(CarCommand command);

    void play();

    void pause();

    void terminate();

}
