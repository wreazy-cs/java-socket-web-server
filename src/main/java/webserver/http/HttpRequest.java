package webserver.http;

import java.util.Map;

/**
 * Ayrıştırılmış bir HTTP isteğini temsil eder. Değiştirilemez (immutable) bir veri sınıfıdır.
 *
 * @param method  GET, HEAD, POST gibi istek yöntemi
 * @param path    Sorgu parametreleri atılmış yol (örn. "/" veya "/index.html")
 * @param version HTTP sürümü (örn. "HTTP/1.1")
 * @param headers Küçük harfe çevrilmiş başlık adları ve değerleri
 */
public record HttpRequest(String method, String path, String version, Map<String, String> headers) {
}
