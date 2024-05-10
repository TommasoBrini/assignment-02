package ex2.worker.concrete;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class ReactiveImpl extends AbstractWorker {
    private final Subject<String> subject;

    public ReactiveImpl() {
        this.subject = PublishSubject.create();
        this.subject.observeOn(Schedulers.computation())
                .subscribe(url -> this.searcher().search(url));
    }

    @Override
    public Type strategy() {
        return Type.REACT;
    }

    @Override
    public void addEventUrl(final String url) {
        this.subject.onNext(url);
    }

    @Override
    public void stop() {
        this.subject.onComplete();
    }

}
