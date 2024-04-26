package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.car.command.concrete.ActionCommand;
import ex1.car.command.concrete.DecideCommand;
import ex1.car.command.concrete.SenseCommand;
import ex1.synchronizers.service.CommandService;
import ex1.synchronizers.worker.slave.Worker;
import ex1.synchronizers.worker.slave.WorkerCarBarrier;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


public class MultiWorkerGeneric extends BaseMasterWorker implements MasterWorker {
    private final List<CarCommand> carCommands;
    private final List<Worker> carsWorkers;
    private final CommandService commandService;
    private int indexCommand;
    private int divisor;

    public MultiWorkerGeneric() {
        this.carsWorkers = new ArrayList<>();
        this.carCommands = List.of(new SenseCommand(), new DecideCommand(), new ActionCommand());
        this.commandService = new CommandService(this);
        this.indexCommand = 0;
        this.divisor = 5;
    }

    public MultiWorkerGeneric(final int divisor) {
        this();
        this.divisor = divisor;
    }

    @Override
    public void setup() {
        final List<List<CarAgent>> carDividedList = ListUtils.divideEqually(this.carAgents(), this.divisor);
        this.commandService.setup(this.carCommands.size());
        carDividedList.forEach(car -> this.carsWorkers.add(new WorkerCarBarrier(null, car)));
    }

    @Override
    public void breakBarrierAction() { }

    @Override
    public boolean hasCommands() {
        return this.indexCommand < this.carCommands.size();
    }

    @Override
    public List<? extends Future<?>> callNextTaskCommand() {
        final CarCommand command = this.carCommands.get(this.indexCommand++);
        System.out.println("\nRUN COMMAND: " + command.getClass().getSimpleName());
        this.carsWorkers.forEach(worker -> worker.setCarCommand(command));
        return this.runTask(this.carsWorkers);
    }

    @Override
    public void execute(final int dt) {
        this.startStopMonitorSimulation().pause();
        this.indexCommand = 0;
        this.setDtToCarAgents(dt);
        this.commandService.runTask(this.callNextTaskCommand());
        this.startStopMonitorSimulation().awaitUntilPlay();
    }

    @Override
    public void terminateWorkers() {
        this.executor.shutdown();
        this.carsWorkers.forEach(Worker::terminate);
    }


}
