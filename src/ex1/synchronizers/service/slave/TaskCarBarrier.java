package ex1.synchronizers.service.slave;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;

import java.util.List;

public class TaskCarBarrier implements Task {
    private final List<CarAgent> agents;
    private CarCommand command;
    private final StartStopMonitor startStopMonitor;
    private final MyCyclicBarrier cyclicBarrier;
    private boolean isRunning;

    public TaskCarBarrier(final MyCyclicBarrier cyclicBarrier, final List<CarAgent> agents) {
        this.startStopMonitor = new StartStopMonitorImpl();
        this.cyclicBarrier = cyclicBarrier;
        this.isRunning = true;
        this.agents = agents;
        this.startStopMonitor.pause();
    }

    @Override
    public void run() {
        this.startStopMonitor.awaitUntilPlay();
        while (this.isRunning) {
            this.execute();
            this.cyclicBarrier.hit();
            this.startStopMonitor.awaitUntilPlay();
        }
        System.out.println("Worker terminated");
    }

    public void play() {
        this.startStopMonitor.play();
    }

    public void pause() {
        this.startStopMonitor.pause();
    }

    public void terminate() {
        this.isRunning = false;
        this.play();
    }

    private void execute() {
        System.out.print("HIT ");
        this.agents.forEach(this.command::execute);
    }

    @Override
    public void setCarCommand(final CarCommand command) {
        this.command = command;
    }

}

