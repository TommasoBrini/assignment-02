package ex1.synchronizers.service.master;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.car.command.concrete.ActionCommand;
import ex1.car.command.concrete.DecideCommand;
import ex1.car.command.concrete.SenseCommand;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrierImpl;
import ex1.synchronizers.service.slave.Task;
import ex1.synchronizers.service.slave.TaskCarBarrier;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiServiceGeneric extends BaseMasterService implements MasterService {

    private final ExecutorService executor;
    private final MyCyclicBarrier cycleBarrier;
    private final List<CarCommand> carCommands;
    private final List<Task> carsTasks;
    private int indexCommand;
    private int divisor;
    private int dt;

    public MultiServiceGeneric() {
        this.carsTasks = new ArrayList<>();
        this.carCommands = List.of(new SenseCommand(), new DecideCommand(), new ActionCommand());
        this.cycleBarrier = new MyCyclicBarrierImpl(this);
        this.indexCommand = 0;
        this.divisor = 5;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public MultiServiceGeneric(final int divisor, final int dt) {
        this();
        this.divisor = divisor;
        this.dt = dt;
    }

    @Override
    public void setup() {
        final List<List<CarAgent>> carDividedList = ListUtils.divideEqually(this.carAgents(), this.divisor);
        this.cycleBarrier.setup(carDividedList.size());
        carDividedList.forEach(car -> this.carsTasks.add(new TaskCarBarrier(this.cycleBarrier, car)));
    }

    @Override
    public void breakBarrierAction() {
        if (this.indexCommand < this.carCommands.size()) {
            final CarCommand command = this.carCommands.get(this.indexCommand++);
            System.out.println("\nRUN COMMAND: " + command.getClass().getSimpleName());
            this.carsTasks.forEach(worker -> worker.setCarCommand(command));
        } else {
            System.out.println("\nWEAK UP SIMULATION");
            this.carsTasks.forEach(Task::pause);
            this.startStopMonitorSimulation().play();
        }
    }

    @Override
    public void execute() {
        this.carsTasks.forEach(this.executor::execute);
        this.startStopMonitorSimulation().pause();
        this.indexCommand = 0;
        this.setDtToCarAgents(this.dt);
        this.breakBarrierAction();
        this.carsTasks.forEach(Task::play);
        this.startStopMonitorSimulation().awaitUntilPlay();
    }

    @Override
    public void terminateWorkers() {
        this.carsTasks.forEach(Task::terminate);
    }

}
