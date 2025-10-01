package ru.otus.java.basic.homeworks.homework33.server.processors;

import ru.otus.java.basic.homeworks.homework33.server.HttpRequest;
import ru.otus.java.basic.homeworks.homework33.server.Responses;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultStaticResourcesProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String filename = httpRequest.getUri().substring(1);
        Path filePath = Paths.get("static/", filename);
        byte[] fileData = Files.readAllBytes(filePath);
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";
        Responses.writeBytes(output, 200, contentType, fileData);
    }
}
