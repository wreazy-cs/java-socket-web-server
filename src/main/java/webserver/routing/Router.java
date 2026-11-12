package webserver.routing;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * İstenen yola göre doğru {@link RequestHandler} nesnesini seçer.
 * Yeni bir sayfa eklemek için bu sınıfı değiştirmek gerekmez, sadece {@link #register} çağrılır.
 */
public class Router implements RequestHandler {

    private final Map<String, RequestHandler> routes = new HashMap<>();
    private final RequestHandler fallback;

    public Router(RequestHandler fallback) {
        this.fallback = fallback;
    }

    public void register(String path, RequestHandler handler) {
        routes.put(path, handler);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return routes.getOrDefault(request.path(), fallback).handle(request);
    }
}
