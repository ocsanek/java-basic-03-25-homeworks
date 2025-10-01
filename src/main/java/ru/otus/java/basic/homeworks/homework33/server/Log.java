package ru.otus.java.basic.homeworks.homework33.server;

import java.util.logging.*;

public final class Log {
    private Log() {}

    public static void initRoot(Level level) {
        Logger root = Logger.getLogger("");
        for (Handler h : root.getHandlers()) root.removeHandler(h);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(level);
        ch.setFormatter(new SimpleFormatter());
        root.addHandler(ch);
        root.setLevel(level);
    }
}
