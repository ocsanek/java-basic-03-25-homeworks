package ru.otus.java.basic.homeworks.homework32.server;

import ru.otus.java.basic.homeworks.homework32.server.processors.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class Dispatcher {
    private final Map<String, RequestProcessor> processors = new HashMap<>();
    private final RequestProcessor defaultNotFoundProcessor = new DefaultNotFoundProcessor();


    public Dispatcher() {
        processors.put("/", new HelloWorldProcessor());
        processors.put("/calculator", new CalculatorProcessor());
        processors.put("/items", new GetItemInfoProcessor());
    }


    public void execute(HttpRequest request, OutputStream output) throws IOException {
        processors.getOrDefault(request.getUri(), defaultNotFoundProcessor)
            .execute(request, output);
    }
}