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
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MultiWorkerGeneric extends BaseMasterWorker implements MasterWorker {
    private final MyCyclicBarrier cycleBarrier;
    private final CyclicBarrier barrier;
    private final List<CarCommand> carCommands;
    private final List<Worker> carsWorkers;
    private int indexCommand;
    private int divisor;

    public MultiWorkerGeneric(final StartStopMonitor starStopMonitorSimulation) {
        super(starStopMonitorSimulation);
        this.carsWorkers = new ArrayList<>();
        this.carCommands = List.of(new SenseCommand(), new DecideCommand(), new ActionCommand());
        this.cycleBarrier = new MyCyclicBarrierImpl(this);
        this.barrier = new CyclicBarrier(2);
        this.indexCommand = 0;
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

    @Override
    public void actionBreakBarrier() {
        if (this.indexCommand < this.carCommands.size()) {
            final CarCommand command = this.carCommands.get(this.indexCommand++);
            System.out.println("\nRUN COMMAND: " + command.getClass().getSimpleName());
            this.carsWorkers.forEach(worker -> worker.setCarCommand(command));
        } else {
            System.out.println("\nWEAK UP SIMULATION");
            this.indexCommand = 0;
            this.startStopMonitorSimulation().play();
            this.carsWorkers.forEach(Worker::pause);
        }
    }

    @Override
    public void execute(final int dt) {
        this.setDtToCarAgents(dt);
        this.actionBreakBarrier();
    }

    @Override
    public void terminateWorkers() {
        this.carsWorkers.forEach(Worker::terminate);
    }


}
