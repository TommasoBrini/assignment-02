package ex2.core.component.concrete;


import ex2.core.component.CounterSearch;
import ex2.core.event.SearchData;
import ex2.core.component.Searcher;

public class CounterSearchImpl implements CounterSearch {
    private static final int STEP_CONSUMER = 1;
    private static final int ZERO = 0;
    private int countConsumeEvent;
    private int countSendEvent;

    public CounterSearchImpl() {
        this.countSendEvent = ZERO;
        this.countConsumeEvent = ZERO;
    }

    @Override
    public void increaseConsumeIfMaxDepth(final SearchData searchData) {
//        if (searchData.currentDepth() == searchData.maxDepth()) {
//            this.countConsumeEvent += STEP_CONSUMER;
//        }
    }

    @Override
    public void increaseSendIfMaxDepth(final Searcher searcher) {
//        final int prevDepth = searcher.maxDepth() - 1;
//        if (searcher.currentDepth() == prevDepth) {
//            this.countSendEvent += searcher.countUrl();
//        }
    }

    @Override
    public boolean isEnd() {
        return this.countSendEvent != ZERO && this.countConsumeEvent == this.countSendEvent;
    }

    @Override
    public void reset() {
        this.countSendEvent = 0;
        this.countConsumeEvent = 0;
    }
}
