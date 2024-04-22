package ex1.synchronizers.worker.slave;

import ex1.car.CarAgent;
import ex1.car.command.CarCommand;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;

import java.util.List;

public class WorkerCarBarrier extends BaseWorker implements Worker {
    private final List<CarAgent> agents;
    private CarCommand command;

    public WorkerCarBarrier(final MyCyclicBarrier cyclicBarrier, final List<CarAgent> agents) {
        super(cyclicBarrier);
        this.agents = agents;
    }

    @Override
    protected void execute() {
        System.out.print("HIT ");
        this.agents.forEach(this.command::execute);
    }

    @Override
    public void setCarCommand(final CarCommand command) {
        this.command = command;
        this.play();
    }

}

