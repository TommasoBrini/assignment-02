package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.car.command.concrete.ActionCommand;
import ex1.car.command.concrete.DecideCommand;
import ex1.car.command.concrete.SenseCommand;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.service.CommandService;
import ex1.synchronizers.worker.slave.Worker;
import ex1.synchronizers.worker.slave.WorkerCarBarrier;
import utils.ListUtils;

import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiWorkerSpecific extends BaseMasterWorker implements MasterWorker {
    private Map<CarCommand, CommandService> commandServiceMap;
    private final Map<CarCommand, List<Worker>> carsWorkersMap;
    private final Map<CarCommand, Integer> commandDivisorMap;
    private final List<CarCommand> commands;
    private int indexCommand;

    public MultiWorkerSpecific() {
        this.carsWorkersMap = new HashMap<>();
        this.commands = List.of(new SenseCommand(), new DecideCommand(), new ActionCommand());
        this.commandDivisorMap = this.commands.stream().collect(Collectors.toMap(command -> command, command -> 5));
        this.commandServiceMap = this.commands.stream().collect(Collectors.toMap(command -> command, command -> new CommandService(this)));
        this.indexCommand = 0;
    }

    public MultiWorkerSpecific(final int sense, final int decide, final int action) {
        this();
        final List<Integer> divisor = List.of(sense, decide, action);
        IntStream.range(0, Math.min(3, this.commands.size())).forEach(i -> this.commandDivisorMap.put(this.commands.get(i), divisor.get(i)));
    }

    @Override
    public void setup() {
        this.commands.forEach(command -> {
            final List<List<CarAgent>> carDividedSenseList = ListUtils.divideEqually(this.carAgents(), this.commandDivisorMap.get(command));
            final CommandService commandService = this.commandServiceMap.get(command);
            commandService.setup(carDividedSenseList.size());


            final List<Worker> workers = carDividedSenseList.stream().map(car -> (Worker) new WorkerCarBarrier(null, car)).toList();
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
    public boolean hasCommands() {
        return this.indexCommand < this.commands.size();
    }

    @Override
    public List<? extends Future<?>> callNextTaskCommand() {

        return List.of();
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
