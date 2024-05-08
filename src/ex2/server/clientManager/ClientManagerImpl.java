package ex2.server.clientManager;

import ex2.core.component.DataEvent;
import ex2.core.listener.ModelListener;
import ex2.core.listener.ViewListener;
import ex2.server.client.ClientService;
import ex2.server.client.JsopClientService;
import ex2.worker.FactoryWorker;
import ex2.worker.LogicWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientManagerImpl implements ClientManager {
    private final ClientService clientService;
    private final FactoryWorker factoryWorker;
    private final List<ViewListener> viewListeners;
    private final List<ModelListener> modelListeners;
    private LogicWorker worker;

    public ClientManagerImpl() {
        this.clientService = new JsopClientService();
        this.viewListeners = new ArrayList<>();
        this.modelListeners = new ArrayList<>();
        this.factoryWorker = new FactoryWorker.FactoryWorkerImpl();
    }

    private void setupWorker(final DataEvent dataEvent) {
        if (Objects.nonNull(this.worker) && dataEvent.workerStrategy().equals(this.worker.strategy())) return;
        this.worker = this.factoryWorker.createWorker(this.clientService, dataEvent.workerStrategy());
        this.modelListeners.forEach(this.worker::addModelListener);
        this.viewListeners.forEach(this.worker::addViewListener);
        this.clientService.clearListener();
        this.clientService.addListenerRequest(this.worker);
        this.worker.start(dataEvent);
    }

    @Override
    public void startSearch(final DataEvent dataEvent) {
        this.setupWorker(dataEvent);
        this.clientService.searchUrl(dataEvent);
    }

    @Override
    public void addViewListener(final ViewListener viewListener) {
        this.viewListeners.add(viewListener);
    }

    @Override
    public void addModelListener(final ModelListener modelListener) {
        this.modelListeners.add(modelListener);
    }

    @Override
    public void stop() {
        this.worker.stop();
    }

}
