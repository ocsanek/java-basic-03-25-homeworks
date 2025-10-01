package ru.otus.java.basic.homeworks.homework31;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        int[] a = {1,2,1,2,2};
        log.info("{}", Arrays.toString(ArrayTasks.tailAfterLastOne(a)));
        try { ArrayTasks.tailAfterLastOne(new int[]{2,2,2}); }
        catch (RuntimeException e) { log.error("Ожидаемое исключение: {}", e.getMessage()); }
        log.info("{}", ArrayTasks.onlyOnesAndTwosWithBothPresent(new int[]{1,2}));
    }
}
