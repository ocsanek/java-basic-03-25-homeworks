package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;
import ru.otus.java.basic.homeworks.homework33.server.error_handling.BadRequestException;

import java.io.IOException;
import java.io.OutputStream;

public class CalculatorProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (!request.containsParameter("a"))
            throw new BadRequestException("Отсутствует обязательный параметр запроса 'a'", "INCORRECT_INPUT_DATA");
        if (!request.containsParameter("b"))
            throw new BadRequestException("Отсутствует обязательный параметр запроса 'b'", "INCORRECT_INPUT_DATA");

        int a, b;
        try {
            a = Integer.parseInt(request.getParameter("a"));
            b = Integer.parseInt(request.getParameter("b"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Параметры 'a' и 'b' должны быть числами", "INCORRECT_INPUT_DATA");
        }

        Responses.writeHtml(output, 200,
            "<html><body><h1>" + a + " + " + b + " = " + (a + b) + "</h1></body></html>");
    }
}
