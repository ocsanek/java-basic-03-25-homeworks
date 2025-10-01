package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;
import ru.otus.java.basic.homeworks.homework33.server.application.ItemsRepository;
import ru.otus.java.basic.homeworks.homework33.server.error_handling.BadRequestException;
import ru.otus.java.basic.homeworks.homework33.server.error_handling.ErrorDto;

import java.io.IOException;
import java.io.OutputStream;

public class DeleteItemProcessor implements RequestProcessor {
    private final ItemsRepository itemsRepository;
    public DeleteItemProcessor(ItemsRepository itemsRepository) { this.itemsRepository = itemsRepository; }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String[] parts = request.getUri().split("/");
        if (parts.length < 3) throw new BadRequestException("Не указан id для удаления", "INCORRECT_INPUT_DATA");
        long id = Long.parseLong(parts[2]);
        boolean removed = itemsRepository.deleteById(id);
        if (removed) {
            Responses.writeHtml(output, 204, "");
        } else {
            Responses.writeJson(output, 404, new ErrorDto("NOT_FOUND", "Товар не найден"));
        }
    }
}
