package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest request, OutputStream output) throws IOException;
}
