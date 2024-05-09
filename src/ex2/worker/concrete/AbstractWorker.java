package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.worker.LogicWorker;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorker implements LogicWorker {
    protected final List<ViewListener> viewListeners;
    protected final List<ModelListener> modelListeners;

    public AbstractWorker() {
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
    }

    @Override
    public void start(final DataEvent dataEvent) {
        this.modelListeners.forEach(listener -> listener.onStart(dataEvent));
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.modelListeners.add(modelListener);
    }

    public abstract void addEventUrl(final DataEvent dataEvent);
}
