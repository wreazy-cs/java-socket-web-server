package webserver.server;

import webserver.http.BadRequestException;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseWriter;
import webserver.http.HttpStatus;
import webserver.routing.RequestHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Tek bir bağlantının yaşam döngüsünü yönetir: isteği okur, yanıtı üretir, yazar ve soketi kapatır.
 * Ayrıştırma, yönlendirme ve yazma işlerini kendisi yapmaz, kendisine verilen bileşenlere devreder.
 */
public class HttpConnectionHandler implements ConnectionHandler {

    private static final int READ_TIMEOUT_MS = 5000;

    private final HttpRequestParser parser;
    private final RequestHandler requestHandler;
    private final HttpResponseWriter writer;

    public HttpConnectionHandler(HttpRequestParser parser, RequestHandler requestHandler, HttpResponseWriter writer) {
        this.parser = parser;
        this.requestHandler = requestHandler;
        this.writer = writer;
    }

    @Override
    public void handle(Socket socket) {
        try (socket) {
            socket.setSoTimeout(READ_TIMEOUT_MS);

            HttpResponse response;
            boolean includeBody = true;
            try {
                HttpRequest request = parser.parse(socket.getInputStream());
                System.out.println(request.method() + " " + request.path()
                        + " (" + socket.getInetAddress().getHostAddress() + ")");
                includeBody = !"HEAD".equals(request.method());
                response = isSupported(request) ? requestHandler.handle(request) : methodNotAllowed();
            } catch (BadRequestException e) {
                response = HttpResponse.error(HttpStatus.BAD_REQUEST);
            } catch (SocketTimeoutException e) {
                return; // Tarayıcı bir şey göndermeden bağlantıyı açık bıraktı.
            } catch (RuntimeException e) {
                System.err.println("Istek islenirken hata: " + e);
                response = HttpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            writer.write(socket.getOutputStream(), response, includeBody);
        } catch (IOException e) {
            System.err.println("Baglanti hatasi: " + e.getMessage());
        }
    }

    private boolean isSupported(HttpRequest request) {
        return "GET".equals(request.method()) || "HEAD".equals(request.method());
    }

    private HttpResponse methodNotAllowed() {
        return HttpResponse.error(HttpStatus.METHOD_NOT_ALLOWED).withHeader("Allow", "GET, HEAD");
    }
}
