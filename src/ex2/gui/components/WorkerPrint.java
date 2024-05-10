package ex2.gui.components;

import ex2.core.component.Searcher;
import ex2.core.event.SearchResponse;
import ex2.gui.area.PrintArea;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class WorkerPrint extends SwingWorker<Void, String> {
    private final PrintArea printArea;
    private final BlockingDeque<SearchResponse> searchers;
    private boolean isRunning;

    public WorkerPrint(final PrintArea printArea) {
        this.printArea = printArea;
        this.searchers = new LinkedBlockingDeque<>();
        this.isRunning = true;
        this.execute();
    }


    public void addSearcher(final SearchResponse response) {
        this.searchers.add(response);
    }


    @Override
    protected Void doInBackground() {
        while (this.isRunning) {
            final SearchResponse searcher = this.searchers.poll();
            if (searcher != null) {
//                System.out.println("WorkerPrint: " + searcher.url() + " " + searcher.countWord() + " " + searcher.currentDepth());
                final String mainUrl = "URL: %s\n".formatted(searcher.url());
                final String info = "Depth[%d] -------- Word = %d\n".formatted(searcher.currentDepth(), searcher.countWord());
                final String text = mainUrl + info + "--------------------\n";
                this.publish(text);
            }
        }
        return null;
    }

    @Override
    protected void process(final List<String> chunks) {
        chunks.forEach(this.printArea::append);
    }

    public void stop() {
        this.isRunning = false;
    }
}
