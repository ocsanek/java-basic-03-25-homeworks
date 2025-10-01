package ru.otus.java.basic.homeworks.homework33.server;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Responses {
    private static final Gson gson = new Gson();
    private Responses() {}

    public static void writeHtml(OutputStream out, int status, String html) throws IOException {
        byte[] body = html.getBytes(StandardCharsets.UTF_8);
        write(out, status, reason(status), "text/html; charset=UTF-8", body, Map.of("Connection", "close"));
    }

    public static void writeJson(OutputStream out, int status, Object obj) throws IOException {
        byte[] body = gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
        write(out, status, reason(status), "application/json; charset=UTF-8", body, Map.of("Connection", "close"));
    }

    public static void writeJson(OutputStream out, int status, Object obj, Map<String, String> extraHeaders) throws IOException {
        byte[] body = gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
        Map<String,String> headers = new LinkedHashMap<>();
        headers.put("Connection","close");
        if (extraHeaders != null) headers.putAll(extraHeaders);
        write(out, status, reason(status), "application/json; charset=UTF-8", body, headers);
    }

    public static void writeBytes(OutputStream out, int status, String contentType, byte[] body) throws IOException {
        write(out, status, reason(status), contentType, body, Map.of("Connection","close"));
    }

    public static void write(OutputStream out, int status, String reason, String contentType, byte[] body, Map<String,String> extraHeaders) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ").append(status).append(' ').append(reason).append("\r\n");
        sb.append("Content-Type: ").append(contentType).append("\r\n");
        sb.append("Content-Length: ").append(body.length).append("\r\n");
        if (extraHeaders != null) {
            for (var e : extraHeaders.entrySet()) {
                sb.append(e.getKey()).append(": ").append(e.getValue()).append("\r\n");
            }
        }
        sb.append("\r\n");
        out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        out.write(body);
    }

    public static String reason(int status) {
        return switch (status) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 204 -> "No Content";
            case 400 -> "Bad Request";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Status";
        };
    }
}
