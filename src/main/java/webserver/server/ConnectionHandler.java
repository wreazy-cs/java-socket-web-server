package webserver.server;

import java.net.Socket;

/**
 * Sunucunun kabul ettiği her bağlantıyı işleyen bileşenin sözleşmesi.
 * {@link WebServer} sadece bu arayüzü bilir, HTTP detaylarından haberi yoktur.
 */
public interface ConnectionHandler {

    void handle(Socket socket);
}
