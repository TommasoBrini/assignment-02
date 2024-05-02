package ex2.core;

import ex2.core.dataEvent.DataEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterFinish {
    private AtomicInteger countConsumeEvent;
    private AtomicInteger countSendEvent;

    public CounterFinish() {
        this.countConsumeEvent = new AtomicInteger();
        this.countSendEvent = new AtomicInteger();
    }

    public void increaseConsumeIfMaxDepth(final DataEvent dataEvent) {
        if (dataEvent.currentDepth() == dataEvent.maxDepth())
            this.countConsumeEvent.incrementAndGet();
    }

    public void increaseSendIfMaxDepth(final DataEvent dataEvent) {
        if (dataEvent.currentDepth() == dataEvent.maxDepth())
            this.countSendEvent.incrementAndGet();
    }

    public boolean isEnd() {
        System.out.println("SEND[" + this.countSendEvent + "] - CONSUME[" + this.countConsumeEvent + "]");
        return this.countConsumeEvent.get() == this.countSendEvent.get();
    }


}
