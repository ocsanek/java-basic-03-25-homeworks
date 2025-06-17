package ru.otus.java.basic.homeworks.homework21;

public class SingleThreadArrayFill {
    public static void main(String[] args) {
        final int size = 100_000_000;
        double[] array = new double[size];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Скорость однопоточного выполнения: " + (endTime - startTime) + "мс.");
    }
}
