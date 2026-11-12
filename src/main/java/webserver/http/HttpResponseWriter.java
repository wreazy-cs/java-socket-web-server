package webserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * {@link HttpResponse} nesnesini HTTP/1.1 biçiminde (durum satırı, başlıklar, boş satır, gövde)
 * sokete yazar.
 */
public class HttpResponseWriter {

    private static final String CRLF = "\r\n";

    public void write(OutputStream out, HttpResponse response, boolean includeBody) throws IOException {
        StringBuilder head = new StringBuilder();
        head.append("HTTP/1.1 ")
                .append(response.status().code()).append(' ')
                .append(response.status().reason()).append(CRLF);
        head.append("Content-Type: ").append(response.contentType()).append(CRLF);
        head.append("Content-Length: ").append(response.body().length).append(CRLF);
        head.append("Connection: close").append(CRLF);
        head.append("Server: W1-JavaServer").append(CRLF);
        for (Map.Entry<String, String> header : response.extraHeaders().entrySet()) {
            head.append(header.getKey()).append(": ").append(header.getValue()).append(CRLF);
        }
        head.append(CRLF);

        out.write(head.toString().getBytes(StandardCharsets.ISO_8859_1));
        if (includeBody) {
            out.write(response.body());
        }
        out.flush();
    }
}
