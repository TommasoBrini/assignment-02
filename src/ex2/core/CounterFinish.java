package ex2.core;

import ex2.core.dataEvent.DataEvent;
import ex2.core.searcher.Searcher;


public class CounterFinish {
    private int countConsumeEvent;
    private int countSendEvent;

    public CounterFinish() {
        this.countConsumeEvent = 0;
        this.countSendEvent = 0;
    }

    public void increaseConsumeIfMaxDepth(final DataEvent dataEvent) {
        if (dataEvent.currentDepth() == dataEvent.maxDepth())
            this.countConsumeEvent += 1;
    }

    public void increaseSendIfMaxDepth(final Searcher searcher) {
        System.out.println(searcher);
        if (searcher.currentDepth() == searcher.maxDepth() - 1) {
            System.out.println("count " + searcher.countUrl());
            this.countSendEvent += searcher.countUrl();
        }
    }

    public boolean isEnd() {
        System.out.println("SEND[" + this.countSendEvent + "] - CONSUME[" + this.countConsumeEvent + "]");
        return this.countConsumeEvent == this.countSendEvent;
    }


}
