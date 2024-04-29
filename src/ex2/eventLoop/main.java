import ex2.eventLoop.Runner;

public static void main(final String[] args) {
    final Runner runner = new Runner();

    try {
        Thread.sleep(1000);
    } catch (final InterruptedException e) {
        e.printStackTrace();
    }

    runner.requestWebClient("https://en.wikipedia.org/wiki/Ubaldo_Ricci");

}