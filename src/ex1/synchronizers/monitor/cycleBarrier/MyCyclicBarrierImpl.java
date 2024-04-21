package ex1.synchronizers.monitor.cycleBarrier;

import java.util.concurrent.atomic.AtomicInteger;

import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;

public class MyCyclicBarrierImpl implements MyCyclicBarrier {
    final StartStopMonitor startStopMonitorWhenBreakBarrier;
    private final AtomicInteger countWorker;
    private int totalWorker;

    public MyCyclicBarrierImpl(final StartStopMonitor startStopMonitorWhenBreakBarrier) {
        this.startStopMonitorWhenBreakBarrier = startStopMonitorWhenBreakBarrier;
        this.countWorker = new AtomicInteger(0);
    }


    @Override
    public void setup(final int parties) {
        this.totalWorker = parties;
    }

    @Override
    public void hit() {
        if (this.countWorker.incrementAndGet() == this.totalWorker) {
            this.countWorker.set(0);
            System.out.println();
            System.out.println("BRAKE BARRIER: play next command");
            this.startStopMonitorWhenBreakBarrier.play();
        }
    }
}
