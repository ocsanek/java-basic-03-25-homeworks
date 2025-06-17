package ru.otus.java.basic.homeworks.homework21;

import javax.swing.plaf.TableHeaderUI;

public class MultiThreadArrayFill {
    public static void main(String[] args) {
        final int size = 100_000_000;
        final int numThreads = 4;
        double[] array = new double[size];
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[numThreads];
        int partSize = size / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int threadIndex = i;
            Thread t = new Thread(() -> {
                int start = threadIndex * size;
                int end = (threadIndex == numThreads - 1) ? array.length : start + size;
                for (int j = start; j < end; j++) {
                    array[j] = 1.14 * Math.cos(j) * Math.sin(j * 0.2) * Math.cos(j / 1.2);
                }
            });
            t.start();
        long endTime = System.currentTimeMillis();
        System.out.println("Скорость многопоточного (" + numThreads + " потока) выполнения: " + (endTime - startTime) + "мс.");
    }
}}

