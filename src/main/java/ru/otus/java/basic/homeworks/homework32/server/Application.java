package ru.otus.java.basic.homeworks.homework32.server;

public class Application {
    public static void main(String[] args) {
        new HttpServer(8189).start();
    }
}