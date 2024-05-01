package ex2;


import ex2.core.history.History;
import ex2.core.history.HistoryImpl;
import ex2.eventLoop.Controller;

public final class Runner {

    public static void main(final String[] args) {
        final Controller controller = new Controller();
        final History history = new HistoryImpl();

    }
}
