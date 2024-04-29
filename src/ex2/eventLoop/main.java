import ex2.eventLoop.core.EventLoopImpl;

public static void main(final String[] args) {
    final EventLoopImpl eventLoop = new EventLoopImpl();
//        eventLoop.createServer();

    try {
        Thread.sleep(1000);
    } catch (final InterruptedException e) {
        e.printStackTrace();
    }

    eventLoop.requestWebClient("https://en.wikipedia.org/wiki/Ubaldo_Ricci");
//        eventLoop.addEvent(null);

}