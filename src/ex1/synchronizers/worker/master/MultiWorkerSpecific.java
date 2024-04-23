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

public class MultiWorkerSpecific extends BaseMasterWorker implements MasterWorker {
    private final List<List<Worker>> carsWorkersList;
    private final List<CarCommand> commands;
    private final MyCyclicBarrier senseCycleBarrier;
    private final MyCyclicBarrier decideCycleBarrier;
    private final MyCyclicBarrier actionCycleBarrier;
    private int senseDivisor;
    private int decideDivisor;
    private int actionDivisor;

    public MultiWorkerSpecific(final StartStopMonitor starStopMonitorSimulation) {
        super(starStopMonitorSimulation);
        this.carsWorkersList = new ArrayList<>();
        this.commands = List.of(new SenseCommand(), new DecideCommand(), new ActionCommand());
        this.senseCycleBarrier = new MyCyclicBarrierImpl(this);
        this.decideCycleBarrier = new MyCyclicBarrierImpl(this);
        this.actionCycleBarrier = new MyCyclicBarrierImpl(this);
        this.senseDivisor = 5;
        this.decideDivisor = 5;
        this.actionDivisor = 5;
    }

    public MultiWorkerSpecific(final StartStopMonitor starStopMonitorSimulation, final int sense, final int decide, final int action) {
        this(starStopMonitorSimulation);
        this.senseDivisor = sense;
        this.decideDivisor = decide;
        this.actionDivisor = action;
    }

    @Override
    public void setup() {
        final List<List<CarAgent>> carDividedSenseList = ListUtils.divideEqually(this.carAgents(), this.senseDivisor);
        final List<List<CarAgent>> carDividedDecideList = ListUtils.divideEqually(this.carAgents(), this.decideDivisor);
        final List<List<CarAgent>> carDividedActionList = ListUtils.divideEqually(this.carAgents(), this.actionDivisor);
        this.senseCycleBarrier.setup(carDividedSenseList.size());
        this.decideCycleBarrier.setup(carDividedDecideList.size());
        this.actionCycleBarrier.setup(carDividedActionList.size());

        final List<Worker> senseWorkers = carDividedSenseList.stream().map(car -> (Worker) new WorkerCarBarrier(this.senseCycleBarrier, car)).toList();
        final List<Worker> decideWorkers = carDividedDecideList.stream().map(car -> (Worker) new WorkerCarBarrier(this.decideCycleBarrier, car)).toList();
        final List<Worker> actionWorkers = carDividedActionList.stream().map(car -> (Worker) new WorkerCarBarrier(this.actionCycleBarrier, car)).toList();

        this.carsWorkersList.add(senseWorkers);
        this.carsWorkersList.add(decideWorkers);
        this.carsWorkersList.add(actionWorkers);
    }

    @Override
    public void execute(final int dt) {
        this.setDtToCarAgents(dt);
        int index = 0;
        for (final var command : this.commands) {
            this.carsWorkersList.get(index++).forEach(worker -> worker.setCarCommand(command));
//            this.startStopMonitorSimulation().pauseAndWaitUntilPlay();
        }
    }

    @Override
    public void terminateWorkers() {
        this.carsWorkersList.forEach(workers -> workers.forEach(Worker::terminate));
    }

    @Override
    public void breakBarrierAction() {

    }
}
