package webserver.routing;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

/**
 * Hiçbir yola uymayan istekler için 404 sayfası döner.
 */
public class NotFoundHandler implements RequestHandler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        return HttpResponse.error(HttpStatus.NOT_FOUND);
    }
}
