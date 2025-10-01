package ru.otus.java.basic.homeworks.homework32.server.processors;


import ru.otus.java.basic.homeworks.homework32.server.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class HelloWorldProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String body = "<html><body><h1>Hello World!!!</h1></body></html>";
        String response = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html; charset=UTF-8\r\n" +
            "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
            "Connection: close\r\n" +
            "\r\n" + body;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}