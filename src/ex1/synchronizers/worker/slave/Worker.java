package ex1.synchronizers.worker.slave;

import ex1.car.command.CarCommand;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;

public interface Worker {

    void play(CarCommand command);

    void terminate();

    void addStartStopMonitorInTail(StartStopMonitor startStopMonitor);
}
