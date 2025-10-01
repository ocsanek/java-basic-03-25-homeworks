package ru.otus.java.basic.homeworks.homework32.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class HttpServer {
    private final int port;
    private final Dispatcher dispatcher = new Dispatcher();
    private final ExecutorService pool;


    public HttpServer(int port) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(64);
    }


    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nОстановка сервера…");
            pool.shutdown();
            try { pool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException ignored) {}
        }));


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(new ClientHandler(socket, dispatcher));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}