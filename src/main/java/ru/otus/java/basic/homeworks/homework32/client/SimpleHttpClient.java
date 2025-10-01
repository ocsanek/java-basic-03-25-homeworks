package ru.otus.java.basic.homeworks.homework32.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class SimpleHttpClient {
    public static void main(String[] args) throws Exception {
        sendGet("/", 8189);
        sendGet("/calculator?a=2&b=40", 8189);
        sendGet("/items?id=5", 8189);
    }


    private static void sendGet(String path, int port) throws Exception {
        try (Socket socket = new Socket("localhost", port)) {
            String req = "GET " + path + " HTTP/1.1\r\n" +
                "Host: localhost:" + port + "\r\n" +
                "Connection: close\r\n\r\n";
            OutputStream out = socket.getOutputStream();
            out.write(req.getBytes(StandardCharsets.UTF_8));
            out.flush();


            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String line;
            System.out.println("\n=== RESPONSE " + path + " ===");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}