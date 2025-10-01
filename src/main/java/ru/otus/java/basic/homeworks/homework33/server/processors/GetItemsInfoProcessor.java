package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;
import ru.otus.java.basic.homeworks.homework33.server.application.ItemsRepository;
import ru.otus.java.basic.homeworks.homework33.server.application.dtos.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GetItemsInfoProcessor implements RequestProcessor {
    private final ItemsRepository itemsRepository;
    public GetItemsInfoProcessor(ItemsRepository itemsRepository) { this.itemsRepository = itemsRepository; }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (request.containsParameter("id")) {
            long id = Long.parseLong(request.getParameter("id"));
            Item item = itemsRepository.getById(id);
            Responses.writeJson(output, 200, item);
        } else {
            List<Item> items = itemsRepository.getAll();
            Responses.writeJson(output, 200, items);
        }
    }
}
