package webserver;

import webserver.http.HttpRequestParser;
import webserver.http.HttpResponseWriter;
import webserver.page.HtmlPageRenderer;
import webserver.page.ProfilePageHandler;
import webserver.page.StaticProfileProvider;
import webserver.routing.NotFoundHandler;
import webserver.routing.RequestHandler;
import webserver.routing.Router;
import webserver.server.ConnectionHandler;
import webserver.server.HttpConnectionHandler;
import webserver.server.WebServer;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * Uygulamanın giriş noktası. Tüm nesneler burada oluşturulup birbirine bağlanır (composition root).
 * Başka hiçbir sınıf somut sınıfları kendisi oluşturmaz, bağımlılıklarını dışarıdan alır.
 */
public final class Main {

    private static final int PORT = 1989;
    private static final int THREAD_COUNT = 8;

    private Main() {
    }

    public static void main(String[] args) {
        RequestHandler homePage = new ProfilePageHandler(new StaticProfileProvider(), new HtmlPageRenderer());

        Router router = new Router(new NotFoundHandler());
        router.register("/", homePage);
        router.register("/index.html", homePage);

        ConnectionHandler connectionHandler =
                new HttpConnectionHandler(new HttpRequestParser(), router, new HttpResponseWriter());

        WebServer server = new WebServer(PORT, connectionHandler, Executors.newFixedThreadPool(THREAD_COUNT));
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        try {
            server.start();
        } catch (IOException e) {
            System.err.println(PORT + " portu acilamadi (baska bir program kullaniyor olabilir): " + e.getMessage());
            System.exit(1);
        }
    }
}
