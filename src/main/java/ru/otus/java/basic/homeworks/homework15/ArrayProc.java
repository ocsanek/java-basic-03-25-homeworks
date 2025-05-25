package ru.otus.java.basic.homeworks.homework15;

import ru.otus.java.basic.homeworks.homework15.exceptions.AppArrayDataException;
import ru.otus.java.basic.homeworks.homework15.exceptions.AppArraySizeException;

public class ArrayProc {
    public static int processArray(String[][] array) throws AppArrayDataException, AppArraySizeException {
        if (array.length != 4) {
            throw new AppArraySizeException("Массив должен быть размером 4х4");
        }
        for (String[] row : array) {
            if (row.length != 4) {
                throw new AppArraySizeException("Каждая строка массива должна содержать 4 элемента");
            }
        }
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new AppArrayDataException("Ошибка в ячейке [" + i + "][" + j + "]: '" +
                            array[i][j] + "'не число");
                }
            }
        }
        return sum;
    }
}
