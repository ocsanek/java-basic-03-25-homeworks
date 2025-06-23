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
            final int threadIndex = i;
            Thread t = new Thread(() -> {
                int start = threadIndex * partSize;
                int end = (threadIndex == numThreads - 1) ? array.length : start + partSize;
                for (int j = start; j < end; j++) {
                    array[j] = 1.14 * Math.cos(j) * Math.sin(j * 0.2) * Math.cos(j / 1.2);
                }
                System.out.println("Поток №" + threadIndex + " закончил обработку от " + start + " до " + (end - 1));
            });
            threads[i] = t;
            t.start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Время многопоточного выполнения (" + numThreads + " потока) " + (endTime - startTime) + "мс.");
    }
}