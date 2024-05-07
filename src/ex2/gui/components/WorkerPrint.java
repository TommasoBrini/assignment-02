package ex2.gui.components;

import ex2.core.component.searcher.Searcher;
import ex2.gui.area.PrintArea;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class WorkerPrint extends SwingWorker<Void, String> {
    private final PrintArea printArea;
    private final BlockingDeque<Searcher> searchers;
    private boolean isRunning;

    public WorkerPrint(final PrintArea printArea) {
        this.printArea = printArea;
        this.searchers = new LinkedBlockingDeque<>();
        this.isRunning = true;
        this.execute();
    }


    public void addSearcher(final Searcher searcher) {
        this.searchers.add(searcher);
    }


    @Override
    protected Void doInBackground() {
        while (this.isRunning) {
            final Searcher searcher = this.searchers.poll();
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
