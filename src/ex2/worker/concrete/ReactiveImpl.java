package ex2.worker.concrete;

import ex2.core.event.SearchResponse;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.List;

public class ReactiveImpl extends AbstractWorker {
    private final Subject<SearchResponse> subject;

    public ReactiveImpl() {
        this.subject = PublishSubject.create();
        this.subject.observeOn(Schedulers.computation())
                .subscribe(response -> {
                    final List<SearchResponse> responses = this.searcher().search(response);
                    this.onResponseView(response);
                    responses.forEach(this::addEventUrl);
                });
    }

    @Override
    public Type strategy() {
        return Type.REACT;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.subject.onNext(response);
    }

    @Override
    public void stop() {
        this.subject.onComplete();
    }

}
