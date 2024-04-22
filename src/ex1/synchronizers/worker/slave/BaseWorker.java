package ex1.synchronizers.worker.slave;

import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrierImpl;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;

public abstract class BaseWorker extends Thread {
    private final StartStopMonitor startStopMonitor;
    private final MyCyclicBarrier cyclicBarrier;
    private boolean isRunning;

    protected BaseWorker(final MyCyclicBarrier cyclicBarrier) {
        this.startStopMonitor = new StartStopMonitorImpl();
        this.cyclicBarrier = cyclicBarrier;
        this.isRunning = true;
        this.start();
    }

    protected abstract void execute();

    @Override
    public void run() {
        this.startStopMonitor.pauseAndWaitUntilPlay();
        while (this.isRunning) {
            this.startStopMonitor.awaitUntilPlay();
            this.execute();
            this.cyclicBarrier.hit();
        }
        System.out.println("Worker terminated");
    }

    protected void play() {
        this.startStopMonitor.play();
    }

    public void pause() {
        this.startStopMonitor.pause();
    }

    public void terminate() {
        this.isRunning = false;
    }



}
