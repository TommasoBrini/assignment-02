package ex2.worker.concrete;

import ex2.core.event.SearchResponse;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.List;
import java.util.stream.Stream;

public class ReactiveImpl extends AbstractWorker {
    private Flowable<SearchResponse> searchObservable;
    private Subject<SearchResponse> searchReportSubject;
    ///SSSS
//    private final Subject<SearchResponse> subject;

    public ReactiveImpl() {
        this.searchObservable = Flowable.empty();
        this.searchReportSubject = PublishSubject.create();
//        this.subject = PublishSubject.create();
//        this.subject.observeOn(Schedulers.computation())
//                .subscribe(response -> {
//                    final List<SearchResponse> responses = this.searcher().search(response);
//                    this.onResponseView(response);
//                    responses.forEach(this::addEventUrl);
//                });
    }

//    public void wordCount(String url, String word, int depth) {
//        this.searchObservable = Flowable.just(url).subscribeOn(Schedulers.io());
//        for (int i = 0; i <= depth; i++) {
//            int index = i;
//            this.searchObservable = this.searchObservable.map(link -> {
////                        if(this.flag.getFlag()){
//                        try {
//
//                            SearchReport report = this.getReport(link, word, index);
//                            this.searchReportSubject.onNext(report);
//                            return report.links().toList();
//                        } catch (Exception e) {
//                            this.errorReportSubject.onNext(new ErrorReport(link, e.getMessage()));
//                            return Stream.of(e.getMessage()).toList();
//                        }
////                        }
//                        return Stream.of(link).toList(); //To change
//                    })
//                    .flatMap(Flowable::fromIterable);
//        }
//        this.searchObservable.doOnComplete(this::reset).subscribe();
//    }

    @Override
    public Type strategy() {
        return Type.REACT;
    }

    @Override
    public void addEventUrl(final SearchResponse response) {
        this.searchObservable = Flowable.just(response).subscribeOn(Schedulers.io());

        for (int i = 0; i < response.maxDepth(); i++) {
            this.searchObservable = (Flowable<SearchResponse>) this.searchObservable.map(res -> {
                try {
                    final List<SearchResponse> list = this.searcher().search(res);
                    list.forEach(res1 -> this.searchReportSubject.onNext(res1));
                    list.forEach(this::onResponseView);
                    return list;
                } catch (Exception e) {
                    return Stream.of(e.getMessage()).toList();
                }
            }).flatMap(source -> Flowable.fromIterable(source));
        }
        this.searchObservable.doOnComplete(this::onFinish).subscribe();
    }

    @Override
    public void stop() {
//        this.subject.onComplete();
    }

}
