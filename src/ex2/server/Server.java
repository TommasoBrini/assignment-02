package ex2.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server {
    private static final String JAVADOC_PATH = "res/docs/api";
    private static final int PORT = 8080;
    private static final int STATUS_CODE = 404;

    private final Vertx vertx;
    private final HttpServer server;
    private final Router router;

    public Server() {
        this.vertx = Vertx.vertx();
        this.server = this.vertx.createHttpServer();
        this.router = Router.router(this.vertx);
    }

    public void run() {
        this.router.get("/*").handler(ctx -> {
            final HttpServerRequest request = ctx.request();
            final String filePath = request.path();
            try {
                final String javadoc = this.getJavadoc(filePath);
                ctx.response()
                        .putHeader("content-type", "text/html")
                        .end(javadoc);
            } catch (final IOException e) {
                ctx.response().setStatusCode(STATUS_CODE).end();
            }
        });

        this.router.route().handler(StaticHandler.create());
        this.server.requestHandler(this.router).listen(PORT);
        System.out.println("Server started on port 8080");
    }

    private String getJavadoc(final String filePath) throws IOException {
        final Path path = Paths.get(JAVADOC_PATH, filePath);
        if (Files.exists(path) && Files.isRegularFile(path) && path.toString().endsWith(".html")) {
            System.out.println("Request for " + filePath);
            return new String(Files.readAllBytes(path));
        } else {
            throw new IOException("File not found or not valid Javadoc file.");
        }
    }

}
