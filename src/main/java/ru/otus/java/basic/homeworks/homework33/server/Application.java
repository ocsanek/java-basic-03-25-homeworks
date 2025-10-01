package ru.otus.java.basic.homeworks.homework33.server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        Log.initRoot(Level.INFO);
        log.info("Starting HTTP server on port 8189");
        new HttpServer(8189).start();
    }
}
