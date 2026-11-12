package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Soketten gelen ham byte akışını {@link HttpRequest} nesnesine çevirir.
 * Tek sorumluluğu istek metnini okumak ve ayrıştırmaktır.
 */
public class HttpRequestParser {

    private static final int MAX_HEADER_COUNT = 100;

    public HttpRequest parse(InputStream in) throws IOException {
        // İstek satırı ve başlıklar ASCII olduğu için ISO_8859_1 yeterlidir.
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.ISO_8859_1));

        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isBlank()) {
            throw new BadRequestException("Boş istek satırı");
        }

        String[] parts = requestLine.trim().split("\\s+");
        if (parts.length != 3) {
            throw new BadRequestException("Geçersiz istek satırı: " + requestLine);
        }

        Map<String, String> headers = new LinkedHashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            if (headers.size() >= MAX_HEADER_COUNT) {
                throw new BadRequestException("Çok fazla başlık");
            }
            int colon = line.indexOf(':');
            if (colon > 0) {
                String name = line.substring(0, colon).trim().toLowerCase(Locale.ROOT);
                String value = line.substring(colon + 1).trim();
                headers.put(name, value);
            }
        }

        String target = parts[1];
        int query = target.indexOf('?');
        String path = query >= 0 ? target.substring(0, query) : target;

        return new HttpRequest(parts[0].toUpperCase(Locale.ROOT), path, parts[2], headers);
    }
}
