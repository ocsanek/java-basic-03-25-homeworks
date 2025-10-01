package ru.otus.java.basic.homeworks.homework32.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Dispatcher dispatcher;


    public ClientHandler(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }


    @Override
    public void run() {
        try (Socket s = socket;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream()) {


            byte[] buffer = new byte[8192];
            int n = in.read(buffer);
            if (n <= 0) return;
            String raw = new String(buffer, 0, n, StandardCharsets.UTF_8);


            HttpRequest request = new HttpRequest(raw);
            request.info(false);


            dispatcher.execute(request, out);
        } catch (Exception e) {
            System.err.println("[ERROR] " + e);
        }
    }
}