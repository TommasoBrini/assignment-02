package ex2.worker;

import ex2.core.component.DataEvent;
import ex2.worker.concrete.WorkerStrategy;

import java.util.List;

public interface WorkerManager extends LogicWorker {

    List<DataEvent> lastHistory();
    void setStrategy(WorkerStrategy workerStrategy);
}
