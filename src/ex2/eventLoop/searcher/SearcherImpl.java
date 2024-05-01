package ex2.eventLoop.searcher;

import ex2.eventLoop.dataEvent.DataEvent;
import ex2.eventLoop.dataEvent.DataEventImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearcherImpl implements Searcher {
    private final WorkerLoop eventLoop;
    private final String urlBody;
    private final DataEvent data;

    public SearcherImpl(final WorkerLoop workerLoop, final DataEvent dataEvent, final String body) {
        this.eventLoop = workerLoop;
        this.data = dataEvent;
        this.urlBody = body;
    }

    @Override
    public int currentDepth() {
        return this.data.currentDepth();
    }

    @Override
    public String url() {
        return this.data.url();
    }

    @Override
    public String word() {
        return this.data.word();
    }

    @Override
    public int countWord() {
        return (int) Arrays.stream(this.urlBody.split(" ")).filter(s -> s.contains(this.word())).count();
    }

    @Override
    public int findUrls() {
        final int counter = 0;
        final List<String> findUrls = new ArrayList<>();
        //cerca href all'interno del body
        final Pattern pattern = Pattern.compile("href=\"(http.*?)\"");
        final Matcher matcher = pattern.matcher(this.urlBody);
        while (matcher.find() && this.currentDepth() < this.data.maxDepth()) {
            DataEvent dt = new DataEventImpl(matcher.group(1), this.word(), this.data.maxDepth(), this.currentDepth() + 1);
            System.out.println(dt);
            this.eventLoop.addEventUrl(dt);
            System.out.println(matcher.group(1));
            findUrls.add(matcher.group(1));
        }
        return findUrls.size();
    }
}
