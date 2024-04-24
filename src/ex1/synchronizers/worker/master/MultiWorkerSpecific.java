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

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MultiWorkerSpecific extends BaseMasterWorker implements MasterWorker {
    private final Map<CarCommand, MyCyclicBarrier> commandCycleBarrierMap;
    private final Map<CarCommand, List<Worker>> carsWorkersMap;
    private final Map<CarCommand, Integer> commandDivisorMap;
    private final List<CarCommand> commands;
    private int indexCommand;

    public MultiWorkerSpecific(final StartStopMonitor starStopMonitorSimulation) {
        super(starStopMonitorSimulation);
        this.carsWorkersMap = new HashMap<>();
        this.commands = List.of(new SenseCommand(), new DecideCommand(), new ActionCommand());
        this.commandDivisorMap = this.commands.stream().collect(Collectors.toMap(command -> command, command -> 5));
        this.commandCycleBarrierMap = this.commands.stream().collect(Collectors.toMap(command -> command, command -> new MyCyclicBarrierImpl(this)));
        this.indexCommand = 0;
    }

    public MultiWorkerSpecific(final StartStopMonitor starStopMonitorSimulation, final int sense, final int decide, final int action) {
        this(starStopMonitorSimulation);
        final List<Integer> divisor = List.of(sense, decide, action);
        IntStream.range(0, Math.min(3, this.commands.size())).forEach(i -> this.commandDivisorMap.put(this.commands.get(i), divisor.get(i)));
    }

    @Override
    public void setup() {
        this.commands.forEach(command -> {
            final List<List<CarAgent>> carDividedSenseList = ListUtils.divideEqually(this.carAgents(), this.commandDivisorMap.get(command));
            final MyCyclicBarrier cyclicBarrier = this.commandCycleBarrierMap.get(command);
            cyclicBarrier.setup(carDividedSenseList.size());
            final List<Worker> workers = carDividedSenseList.stream().map(car -> (Worker) new WorkerCarBarrier(cyclicBarrier, car)).toList();
            this.carsWorkersMap.put(command, workers);
        });
    }


    @Override
    public void breakBarrierAction() {
        if (this.indexCommand < this.commands.size()) {
            if (this.indexCommand - 1 >= 0) {
                this.carsWorkersMap.get(this.commands.get(this.indexCommand - 1)).forEach(Worker::pause);
            }
            final CarCommand command = this.commands.get(this.indexCommand++);
            final List<Worker> workers = this.carsWorkersMap.get(command);
            System.out.println("\nRUN COMMAND: " + command.getClass().getSimpleName());
            workers.forEach(worker -> worker.setCarCommand(command));
            workers.forEach(Worker::play);
        } else {
            System.out.println("\nWEAK UP SIMULATION");
            this.carsWorkersMap.get(this.commands.get(this.indexCommand - 1)).forEach(Worker::pause);
            this.startStopMonitorSimulation().play();
        }
    }

    @Override
    public void execute(final int dt) {
        this.startStopMonitorSimulation().pause();
        this.indexCommand = 0;
        this.setDtToCarAgents(dt);
        this.breakBarrierAction();
        this.startStopMonitorSimulation().awaitUntilPlay();
    }

    @Override
    public void terminateWorkers() {
        this.carsWorkersMap.values().forEach(workers -> workers.forEach(Worker::terminate));
    }

}
