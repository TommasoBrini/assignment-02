package ex2.worker.concrete;

import ex2.core.component.DataEvent;
import ex2.web.client.ClientService;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class ReactiveImpl extends AbstractWorker {
    private final Subject<DataEvent> subject;

    public ReactiveImpl(final ClientService clientService) {
        super(clientService);
        this.subject = PublishSubject.create();
        this.subject.observeOn(Schedulers.computation()).subscribe(dataEvent -> this.clientService.onSearch(dataEvent.url()));
    }

    @Override
    public void addEventUrl(final DataEvent dataEvent) {
        this.subject.onNext(dataEvent);
    }

    @Override
    public WorkerStrategy strategy() {
        return WorkerStrategy.REACT;
    }

    @Override
    public void stop() {
        this.subject.onComplete();
    }

}
