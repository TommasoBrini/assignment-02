package ex1.synchronizers.worker.master;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.synchronizers.service.CommandService;
import ex1.synchronizers.worker.slave.Worker;
import ex1.synchronizers.worker.slave.WorkerCarBarrier;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


public class MultiWorkerGeneric extends BaseMasterWorker implements MasterWorker {
    private final List<Worker> carsWorkers;
    private final CommandService commandService;
    private int divisor;

    public MultiWorkerGeneric() {
        this.carsWorkers = new ArrayList<>();
        this.commandService = new CommandService(this);
        this.divisor = 5;
    }

    public MultiWorkerGeneric(final int divisor) {
        this();
        this.divisor = divisor;
    }

    @Override
    public void setup() {
        final List<List<CarAgent>> carDividedList = ListUtils.divideEqually(this.carAgents(), this.divisor);
        this.commandService.setup(carDividedList.size());
        carDividedList.forEach(car -> this.carsWorkers.add(new WorkerCarBarrier(car)));
    }

    @Override
    public List<? extends Future<?>> callNextTaskCommand() {
        final CarCommand command = this.nextCommand();
        System.out.println("\nRUN COMMAND: " + command.getClass().getSimpleName());
        this.carsWorkers.forEach(worker -> worker.setCarCommand(command));
        return this.runTask(this.carsWorkers);
    }

    @Override
    public void execute(final int dt) {
        this.startStopMonitorSimulation().pause();
        this.resetIndexCommand();
        this.setDtToCarAgents(dt);
        this.commandService.runTask(this.callNextTaskCommand());
        this.startStopMonitorSimulation().awaitUntilPlay();
    }

}
