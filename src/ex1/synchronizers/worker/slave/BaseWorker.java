package ex1.synchronizers.worker.slave;

import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;

public abstract class BaseWorker extends Thread implements StartStopMonitor {
    private final StartStopMonitor startStopMonitor;
    private boolean isRunning;

    protected BaseWorker() {
        this.startStopMonitor = new StartStopMonitorImpl();
        this.isRunning = true;
        this.start();
    }

    protected abstract void execute();

    @Override
    public void run() {
        this.pause();
        this.awaitUntilPlay();
        while (this.isRunning) {
            this.execute();
            this.pauseAndWaitUntilPlay();
        }
        System.out.println("Worker terminated");
    }

    @Override
    public void play() {
        this.startStopMonitor.play();
    }
    @Override
    public void pause() {
        this.startStopMonitor.pause();
    }
    @Override
    public void awaitUntilPlay() {
        this.startStopMonitor.awaitUntilPlay();
    }
    @Override
    public void pauseAndWaitUntilPlay() {
        this.startStopMonitor.pauseAndWaitUntilPlay();
    }

    public void terminate() {
        this.play();
        this.isRunning = false;
    }



}
