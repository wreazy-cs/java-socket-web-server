package webserver.page;

/**
 * Bir {@link Profile} nesnesini tarayıcıya gönderilecek metne çeviren bileşenin sözleşmesi.
 */
public interface PageRenderer {

    String render(Profile profile);
}
