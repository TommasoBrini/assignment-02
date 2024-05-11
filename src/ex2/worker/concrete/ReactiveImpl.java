package ex2.worker.concrete;

import ex2.core.event.SearchResponse;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.List;
import java.util.stream.IntStream;

public class ReactiveImpl extends AbstractWorker {
    private Flowable<SearchResponse> searchObservable;
    private final Subject<SearchResponse> searchReportSubject;

    public ReactiveImpl() {
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
    }

    @Override
    public Type strategy() {
        return Type.REACT;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.onResponseView(response);
        this.searchObservable = Flowable.just(response).subscribeOn(Schedulers.computation());

        IntStream.range(0, response.maxDepth()).forEach(i ->
                this.searchObservable = this.searchObservable.map(res -> {
                    final List<SearchResponse> list = this.searcher().search(res);
                    list.forEach(this.searchReportSubject::onNext);
                    list.forEach(this::onResponseView);
                    return list;
                }).flatMap(Flowable::fromIterable));
        this.searchObservable.doOnComplete(this::onFinishListener).subscribe();
    }

    @Override
    public void stop() {

    }

}
