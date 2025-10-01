package ru.otus.java.basic.homeworks.homework33.server.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;
import ru.otus.java.basic.homeworks.homework33.server.application.ItemsRepository;
import ru.otus.java.basic.homeworks.homework33.server.application.dtos.Item;
import ru.otus.java.basic.homeworks.homework33.server.error_handling.BadRequestException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class CreateNewItemProcessor implements RequestProcessor {
    private final ItemsRepository itemsRepository;
    private final Gson gson = new Gson();

    public CreateNewItemProcessor(ItemsRepository itemsRepository) { this.itemsRepository = itemsRepository; }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String ct = request.getHeader("Content-Type");
        if (ct == null || !ct.toLowerCase().contains("application/json")) {
            throw new BadRequestException("Ожидается Content-Type: application/json", "INCORRECT_INPUT_DATA");
        }
        Item item = gson.fromJson(request.getBody(), Item.class);
        itemsRepository.createNew(item);
        Responses.writeJson(output, 201, item, Map.of("Location", "/items?id=" + item.getId()));
    }
}
