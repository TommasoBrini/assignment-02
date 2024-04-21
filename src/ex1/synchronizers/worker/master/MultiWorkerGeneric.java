package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.car.command.concrete.ActionCommand;
import ex1.car.command.concrete.DecideCommand;
import ex1.car.command.concrete.SenseCommand;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrierImpl;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.worker.slave.Worker;
import ex1.synchronizers.worker.slave.WorkerCarBarrier;
import ex1.synchronizers.worker.slave.Worker;
import ex1.synchronizers.worker.slave.WorkerCarBarrier;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrierImpl;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class MultiWorkerGeneric extends BaseMasterWorker implements MasterWorker {
    private final List<Worker> carsWorkers;
    private final MyCyclicBarrier cycleBarrier;
    private int divisor;

    public MultiWorkerGeneric(final StartStopMonitor starStopMonitorSimulation) {
        super(starStopMonitorSimulation);
        this.carsWorkers = new ArrayList<>();
        this.cycleBarrier = new MyCyclicBarrierImpl(this.startStopMonitorSimulation());
        this.divisor = 5;
    }

    public MultiWorkerGeneric(final StartStopMonitor starStopMonitorSimulation, final int divisor) {
        this(starStopMonitorSimulation);
        this.divisor = divisor;
    }

    @Override
    public void setup() {
        final List<List<CarAgent>> carDividedList = ListUtils.divideEqually(this.carAgents(), this.divisor);
        this.cycleBarrier.setup(carDividedList.size());
        carDividedList.forEach(car -> this.carsWorkers.add(new WorkerCarBarrier(this.cycleBarrier, car)));
    }

    private void runCommand(final CarCommand command) {
        System.out.println("RUN COMMAND: " + command.getClass().getSimpleName());
        this.carsWorkers.forEach(worker -> worker.play(command));
        this.startStopMonitorSimulation().pauseAndWaitUntilPlay();
    }

    @Override
    public void execute(final int dt) {
        this.setDtToCarAgents(dt);
        this.runCommand(new SenseCommand());
        this.runCommand(new DecideCommand());
        this.runCommand(new ActionCommand());
    }

    @Override
    public void terminateWorkers() {
        this.carsWorkers.forEach(Worker::terminate);
    }

}
