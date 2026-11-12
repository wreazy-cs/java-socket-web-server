package webserver.routing;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

/**
 * Bir HTTP isteğini alıp yanıt üreten her şeyin ortak sözleşmesi.
 * Sayfalar, hata sayfaları ve yönlendirici (Router) bu arayüzü uygular.
 */
public interface RequestHandler {

    HttpResponse handle(HttpRequest request);
}
