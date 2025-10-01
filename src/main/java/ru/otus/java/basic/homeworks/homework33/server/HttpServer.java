package ru.otus.java.basic.homeworks.homework33.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpServer {
    private static final Logger log = Logger.getLogger(HttpServer.class.getName());

    private final int port;
    private final Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("HTTP server started on port " + port);
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    byte[] buffer = new byte[8192];
                    int n = socket.getInputStream().read(buffer);
                    if (n < 1) continue;
                    String rawRequest = new String(buffer, 0, n);
                    HttpRequest request = new HttpRequest(rawRequest);
                    log.fine(() -> request.getMethod() + " " + request.getUri());
                    dispatcher.execute(request, socket.getOutputStream());
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Server error", e);
        }
    }
}
