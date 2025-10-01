package ru.otus.java.basic.homeworks.homework32.server.processors;


import com.google.gson.Gson;
import ru.otus.java.basic.homeworks.homework32.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework32.server.application.dtos.Item;


import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;


public class GetItemInfoProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Item item = new Item(id, "Milk", BigDecimal.valueOf(90 + id % 20));
        String json = new Gson().toJson(item);


        String headers = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: application/json; charset=UTF-8\r\n" +
            "Content-Length: " + json.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
            "Connection: close\r\n\r\n";
        output.write(headers.getBytes(StandardCharsets.UTF_8));
        output.write(json.getBytes(StandardCharsets.UTF_8));
    }
}
