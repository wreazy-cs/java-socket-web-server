package webserver.page;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.routing.RequestHandler;

/**
 * Ana sayfa isteğine profil sayfasıyla cevap verir.
 * Veriyi {@link ProfileProvider}'dan alır, HTML'i {@link PageRenderer}'a yaptırır.
 */
public class ProfilePageHandler implements RequestHandler {

    private final ProfileProvider profileProvider;
    private final PageRenderer renderer;

    public ProfilePageHandler(ProfileProvider profileProvider, PageRenderer renderer) {
        this.profileProvider = profileProvider;
        this.renderer = renderer;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        String html = renderer.render(profileProvider.getProfile());
        return HttpResponse.html(HttpStatus.OK, html);
    }
}
