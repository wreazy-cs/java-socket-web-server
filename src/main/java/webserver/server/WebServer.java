package webserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;

/**
 * Belirtilen portu dinleyen ve gelen her bağlantıyı bir iş parçacığı havuzuna devreden sunucu.
 * Tek sorumluluğu bağlantı kabul etmektir; isteğin içeriğiyle ilgilenmez.
 */
public class WebServer {

    private final int port;
    private final ConnectionHandler connectionHandler;
    private final ExecutorService pool;

    private volatile ServerSocket serverSocket;
    private volatile boolean running;

    public WebServer(int port, ConnectionHandler connectionHandler, ExecutorService pool) {
        this.port = port;
        this.connectionHandler = connectionHandler;
        this.pool = pool;
    }

    /** Sunucuyu başlatır ve stop() çağrılana kadar bağlantı kabul eder. */
    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Sunucu calisiyor: http://localhost:" + port);

        while (running) {
            try {
                Socket client = serverSocket.accept();
                pool.execute(() -> connectionHandler.handle(client));
            } catch (SocketException e) {
                // stop() soketi kapattığında accept() bu hatayı verir, bu normal bir kapanıştır.
                if (running) {
                    throw e;
                }
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
            // Kapanırken oluşan hata önemsizdir.
        }
        pool.shutdown();
        System.out.println("Sunucu durduruldu.");
    }
}
