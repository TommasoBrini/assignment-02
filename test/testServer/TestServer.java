package testServer;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestServer {
    private static final String JAVADOC_PATH = "res/docs/docs/api";
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        // Gestisce le richieste per la Javadoc
        router.get("/*").handler(ctx -> {
            HttpServerRequest request = ctx.request();
            String filePath = request.path();
            try {
                String javadoc = getJavadoc(filePath);
                ctx.response()
                        .putHeader("content-type", "text/html")
                        .end(javadoc);
            } catch (IOException e) {
                ctx.response().setStatusCode(404).end();
            }

        });

        // Gestisce le richieste per i file statici
        router.route().handler(StaticHandler.create());

        server.requestHandler(router).listen(8080);
        System.out.println("Server started on port 8080");
    }

    private static String getJavadoc(String filePath) throws IOException {
        Path path = Paths.get(JAVADOC_PATH, filePath);
        if (Files.exists(path) && Files.isRegularFile(path) && path.toString().endsWith(".html")) {
            System.out.println("Request for " + filePath);
            return new String(Files.readAllBytes(path));
        } else {
            throw new IOException("File not found or not valid Javadoc file.");
        }
    }
}
