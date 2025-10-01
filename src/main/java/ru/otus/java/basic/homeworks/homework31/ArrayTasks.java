package ru.otus.java.basic.homeworks.homework31;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class ArrayTasks {
    private static final Logger log = LogManager.getLogger(ArrayTasks.class);

    // Возвращает элементы после последней 1. Если единицы нет — RuntimeException
    public static int[] tailAfterLastOne(int[] input) {
        if (input == null) throw new IllegalArgumentException("input must not be null");
        log.info("tailAfterLastOne: {}", Arrays.toString(input));

        int lastOne = -1;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 1) lastOne = i;
        }
        if (lastOne == -1) {
            log.error("Нет единиц в {}", Arrays.toString(input));
            throw new RuntimeException("Array does not contain 1");
        }
        int[] result = Arrays.copyOfRange(input, lastOne + 1, input.length);
        log.info("tailAfterLastOne -> {}", Arrays.toString(result));
        return result;
    }

    //True если массив содержит ТОЛЬКО 1 и 2
    // есть хотя бы одна 1 и хотя бы одна 2.
    // null — IllegalArgumentException
    public static boolean onlyOnesAndTwosWithBothPresent(int[] input) {
        if (input == null || input.length == 0)
            throw new IllegalArgumentException("input must not be null or empty");

        log.info("onlyOnesAndTwosWithBothPresent: {}", Arrays.toString(input));
        boolean has1 = false, has2 = false;

        for (int v : input) {
            if (v == 1) has1 = true;
            else if (v == 2) has2 = true;
            else return false; // есть другое число
        }
        return has1 && has2;
    }
}