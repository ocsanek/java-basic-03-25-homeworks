package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultNotFoundProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Responses.writeHtml(output, 404, "<html><body><h1>Resource Not Found!!!</h1></body></html>");
    }
}
