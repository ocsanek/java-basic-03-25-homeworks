package ru.otus.java.basic.homeworks.homework32.server;

import java.util.HashMap;
import java.util.Map;


public class HttpRequest {
    private final String rawRequest;
    private String method;
    private String uri;
    private final Map<String, String> parameters;


    public String getMethod() { return method; }
    public String getUri() { return uri; }
    public String getParameter(String key) { return parameters.get(key); }


    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parameters = new HashMap<>();
        parse();
    }


    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        method = rawRequest.substring(0, startIndex);
        uri = rawRequest.substring(startIndex + 1, endIndex);
        if (uri.contains("?")) {
            String[] parts = uri.split("[?]", 2);
            uri = parts[0];
            if (parts.length > 1) {
                for (String kv : parts[1].split("&")) {
                    String[] pair = kv.split("=", 2);
                    parameters.put(pair[0], pair.length > 1 ? pair[1] : "");
                }
            }
        }
    }


    public void info(boolean debug) {
        if (debug) System.out.println(rawRequest);
        System.out.println("METHOD: " + method);
        System.out.println("URI: " + uri);
        System.out.println("PARAMETERS: " + parameters);
    }
}