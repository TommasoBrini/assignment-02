package ex2.worker;

import ex2.core.component.DataEvent;

import java.util.List;

public interface WorkerManager extends LogicWorker {

    List<DataEvent> lastHistory();
}
