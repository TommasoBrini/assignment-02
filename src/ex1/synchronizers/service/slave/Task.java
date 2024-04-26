package ex1.synchronizers.service.slave;

import ex1.car.command.CarCommand;

public interface Task extends Runnable {

    void setCarCommand(CarCommand command);

    void play();

    void pause();

    void terminate();

}
