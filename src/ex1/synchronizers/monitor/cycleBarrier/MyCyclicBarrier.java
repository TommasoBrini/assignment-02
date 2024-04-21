package ex1.synchronizers.monitor.cycleBarrier;

public interface MyCyclicBarrier {

    void setup(final int parties);

    void hit();

}
