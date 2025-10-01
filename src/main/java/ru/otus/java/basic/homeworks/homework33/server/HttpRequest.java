package ru.otus.java.basic.homeworks.homework33.server;

import java.util.*;

public class HttpRequest {
    private final String rawRequest;
    private String method;
    private String uri;
    private String body = "";
    private final Map<String, String> parameters = new LinkedHashMap<>();
    private final Map<String, String> headers = new LinkedHashMap<>();

    public String getMethod() { return method; }
    public String getUri() { return uri; }
    public String getRoutingKey() { return method + " " + uri; }
    public String getParameter(String key) { return parameters.get(key); }
    public boolean containsParameter(String key) { return parameters.containsKey(key); }
    public String getBody() { return body; }
    public Map<String, String> getHeaders() { return Collections.unmodifiableMap(headers); }
    public String getHeader(String name) {
        for (var e : headers.entrySet()) {
            if (e.getKey().equalsIgnoreCase(name)) return e.getValue();
        }
        return null;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        parse();
    }

    private void parse() {
        int sep = rawRequest.indexOf("\r\n\r\n");
        String head = sep >= 0 ? rawRequest.substring(0, sep) : rawRequest;
        if (sep >= 0 && sep + 4 <= rawRequest.length()) {
            body = rawRequest.substring(sep + 4);
        }

        String[] lines = head.split("\r\n");
        if (lines.length == 0) return;

        // request line
        int sp1 = lines[0].indexOf(' ');
        int sp2 = lines[0].indexOf(' ', sp1 + 1);
        method = lines[0].substring(0, sp1);
        uri = lines[0].substring(sp1 + 1, sp2);

        // query params
        if (uri.contains("?")) {
            String[] parts = uri.split("\\?", 2);
            uri = parts[0];
            if (parts.length > 1) {
                for (String kv : parts[1].split("&")) {
                    String[] p = kv.split("=", 2);
                    parameters.put(p[0], p.length > 1 ? p[1] : "");
                }
            }
        }

        // headers
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            int colon = line.indexOf(':');
            if (colon > 0) {
                String name = line.substring(0, colon).trim();
                String value = line.substring(colon + 1).trim();
                headers.put(name, value);
            }
        }
    }
}
