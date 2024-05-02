package ex2.core;

import ex2.core.dataEvent.DataEvent;
import ex2.core.searcher.Searcher;


public class CounterSearch {
    private static final int STEP_CONSUMER = 1;
    private static final int ZERO = 0;
    private int countConsumeEvent;
    private int countSendEvent;

    public CounterSearch() {
        this.countSendEvent = ZERO;
        this.countConsumeEvent = ZERO;
    }

    public void increaseConsumeIfMaxDepth(final DataEvent dataEvent) {
        if (dataEvent.currentDepth() == dataEvent.maxDepth()) {
            this.countConsumeEvent += STEP_CONSUMER;
        }
    }

    public void increaseSendIfMaxDepth(final Searcher searcher) {
        final int prevDepth = searcher.maxDepth() - 1;
        if (searcher.currentDepth() == prevDepth) {
            this.countSendEvent += searcher.countUrl();
        }
    }

    public boolean isEnd() {
        return this.countSendEvent != ZERO && this.countConsumeEvent == this.countSendEvent;
    }


    public void reset() {
        this.countSendEvent = 0;
        this.countConsumeEvent = 0;
    }
}
