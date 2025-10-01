package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;
import ru.otus.java.basic.homeworks.homework33.server.application.ItemsRepository;
import ru.otus.java.basic.homeworks.homework33.server.error_handling.BadRequestException;
import ru.otus.java.basic.homeworks.homework33.server.error_handling.ErrorDto;
import ru.otus.java.basic.homeworks.homework33.server.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dispatcher {
    private static final Logger log = Logger.getLogger(Dispatcher.class.getName());

    private final Map<String, RequestProcessor> processors = new HashMap<>();
    private final RequestProcessor defaultNotFoundProcessor = new DefaultNotFoundProcessor();
    private final RequestProcessor defaultStaticResourcesProcessor = new DefaultStaticResourcesProcessor();

    private final ItemsRepository itemsRepository = new ItemsRepository();
    private final RequestProcessor deleteItemProcessor = new DeleteItemProcessor(itemsRepository); // опционально

    public Dispatcher() {
        processors.put("GET /", new HelloWorldProcessor());
        processors.put("GET /calculator", new CalculatorProcessor());
        processors.put("GET /items", new GetItemsInfoProcessor(itemsRepository));
        processors.put("POST /items", new CreateNewItemProcessor(itemsRepository));
    }

    public void execute(HttpRequest request, OutputStream output) throws IOException {
        try {
            // статика
            if (Files.exists(Paths.get("static/", request.getUri().substring(1)))) {
                defaultStaticResourcesProcessor.execute(request, output);
                return;
            }
            // DELETE /items/{id} — под ваш фронт
            if ("DELETE".equals(request.getMethod()) && request.getUri().startsWith("/items/")) {
                deleteItemProcessor.execute(request, output);
                return;
            }

            RequestProcessor p = processors.get(request.getRoutingKey());
            if (p == null) {
                log.info(() -> "404 " + request.getRoutingKey());
                defaultNotFoundProcessor.execute(request, output);
                return;
            }
            p.execute(request, output);
        } catch (BadRequestException e) {
            log.log(Level.WARNING, "400 " + e.getMessage());
            Responses.writeJson(output, 400, new ErrorDto(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            log.log(Level.SEVERE, "500 " + e.getMessage(), e);
            Responses.writeJson(output, 500, new ErrorDto("INTERNAL_ERROR", "Внутренняя ошибка сервера"));
        }
    }
}
