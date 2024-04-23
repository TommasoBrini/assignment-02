package ex1.synchronizers.monitor.cycleBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import ex1.synchronizers.worker.master.MasterWorker;

public class MyCyclicBarrierImpl implements MyCyclicBarrier {
    private final MasterWorker masterWorker;
    private CyclicBarrier cyclicBarrier;

    public MyCyclicBarrierImpl(final MasterWorker masterWorker) {
        this.masterWorker = masterWorker;
    }


    @Override
    public void setup(final int parties) {
        this.cyclicBarrier = new CyclicBarrier(parties, this.masterWorker::breakBarrierAction);
    }

    @Override
    public void hit() {
        try {
            this.cyclicBarrier.await();
        } catch (final InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
