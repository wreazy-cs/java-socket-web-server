package webserver.http;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tarayıcıya geri gönderilecek HTTP yanıtını temsil eder.
 * Yanıtın nasıl yazıldığı (ağa gönderilmesi) bu sınıfın değil {@link HttpResponseWriter} sınıfının işidir.
 */
public final class HttpResponse {

    private final HttpStatus status;
    private final String contentType;
    private final byte[] body;
    private final Map<String, String> extraHeaders = new LinkedHashMap<>();

    public HttpResponse(HttpStatus status, String contentType, byte[] body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    /** UTF-8 kodlu bir HTML yanıtı üretir. */
    public static HttpResponse html(HttpStatus status, String html) {
        return new HttpResponse(status, "text/html; charset=UTF-8", html.getBytes(StandardCharsets.UTF_8));
    }

    /** Verilen durum için basit bir hata sayfası üretir. */
    public static HttpResponse error(HttpStatus status) {
        String title = status.code() + " " + status.reason();
        String page = "<!DOCTYPE html><html lang=\"tr\"><head><meta charset=\"UTF-8\">"
                + "<title>" + title + "</title></head>"
                + "<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:80px\">"
                + "<h1>" + title + "</h1></body></html>";
        return html(status, page);
    }

    public HttpResponse withHeader(String name, String value) {
        extraHeaders.put(name, value);
        return this;
    }

    public HttpStatus status() {
        return status;
    }

    public String contentType() {
        return contentType;
    }

    public byte[] body() {
        return body;
    }

    public Map<String, String> extraHeaders() {
        return Collections.unmodifiableMap(extraHeaders);
    }
}
