package ru.otus.java.basic.homeworks.homework15;

import ru.otus.java.basic.homeworks.homework15.exceptions.AppArrayDataException;
import ru.otus.java.basic.homeworks.homework15.exceptions.AppArraySizeException;

public class Main {
    public static void main(String[] args) {
        String[][] correctArray = {
                {"1","2","3","4"},
                {"5","6","7","8"},
                {"9","10","11","12"},
                {"13","14","15","106"}
        };

        String[][] invalidArraySize = {
                {"1","2","3"},
                {"4","5","6"},
                {"7","8","9"}
        };

        String[][] invalidArrayData = {
                {"1","2","3","4"},
                {"5","6","x","8"},
                {"9","10","11","12"},
                {"13","14","15","16"}
        };

        try {
            int result = ArrayProc.processArray(correctArray);
            System.out.println("Сумма элементов массива равна - " + result);
        } catch (AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            int result = ArrayProc.processArray(invalidArraySize);
            System.out.println("Сумма элементов массива равна - " + result);
        } catch (AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            int result = ArrayProc.processArray(invalidArrayData);
            System.out.println("Сумма элементов массива равна - " + result);
        } catch (AppArraySizeException | AppArrayDataException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
