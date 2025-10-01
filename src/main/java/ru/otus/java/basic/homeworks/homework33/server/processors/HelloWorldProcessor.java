package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;

import java.io.IOException;
import java.io.OutputStream;

public class HelloWorldProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Responses.writeHtml(output, 200, "<html><body><h1>Hello World!!!</h1></body></html>");
    }
}
