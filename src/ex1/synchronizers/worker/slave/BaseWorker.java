package ex1.synchronizers.worker.slave;

import ex1.synchronizers.monitor.cycleBarrier.MyCyclicBarrier;
import ex1.synchronizers.monitor.startStop.StartStopMonitor;
import ex1.synchronizers.monitor.startStop.StartStopMonitorImpl;

public abstract class BaseWorker {
    private final StartStopMonitor startStopMonitor;
    private final MyCyclicBarrier cyclicBarrier;
    private boolean isRunning;

    protected BaseWorker(final MyCyclicBarrier cyclicBarrier) {
        this.startStopMonitor = new StartStopMonitorImpl();
        this.cyclicBarrier = cyclicBarrier;
        this.isRunning = true;
        this.startStopMonitor.pause();
//        this.start();
    }

    protected abstract void execute();


    public Void call() {
//        this.startStopMonitor.awaitUntilPlay();
//        while (this.isRunning) {
            this.execute();
            // qui conta quanti task hanno finito
//            this.cyclicBarrier.hit();
//            this.startStopMonitor.awaitUntilPlay();
//        }

        // azione finale
//        System.out.println("Worker terminated");
        return null;
    }

//    @Override
//    public void run() {
//        this.startStopMonitor.awaitUntilPlay();
//        while (this.isRunning) {
//            this.execute();
//            this.cyclicBarrier.hit();
//            this.startStopMonitor.awaitUntilPlay();
//        }
//        System.out.println("Worker terminated");
//    }

    public void play() {
        this.startStopMonitor.play();
    }

    public void pause() {
        this.startStopMonitor.pause();
    }

    public void terminate() {
        this.isRunning = false;
        this.play();
    }



}
