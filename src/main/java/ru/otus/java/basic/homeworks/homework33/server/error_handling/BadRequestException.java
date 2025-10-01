package ru.otus.java.basic.homeworks.homework33.server.error_handling;

public class BadRequestException extends RuntimeException {
    private final String code;
    public String getCode() { return code; }
    public BadRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
