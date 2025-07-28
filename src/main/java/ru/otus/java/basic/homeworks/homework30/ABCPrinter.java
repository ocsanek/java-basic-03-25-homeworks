package ru.otus.java.basic.homeworks.homework30;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrinter {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condA = lock.newCondition();
    private final Condition condB = lock.newCondition();
    private final Condition condC = lock.newCondition();
    private int state = 0;

    public void printA() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (state != 0) {
                    condA.await();
                }
                System.out.print("A");
                state = 1;
                condB.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printB() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (state != 1) {
                    condB.await();
                }
                System.out.print("B");
                state = 2;
                condC.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public void printC() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (state != 2) {
                    condC.await();
                }
                System.out.print("C");
                state = 0;
                condA.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ABCPrinter printer = new ABCPrinter();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(printer::printA);
        executor.execute(printer::printB);
        executor.execute(printer::printC);

        executor.shutdown();
    }
}