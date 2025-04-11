package ru.otus.java.basic.homeworks.homework3;

import java.util.Random;
import java.util.Scanner;

public class Homework3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Введите число от 1 до 5:");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                greetings();
                break;
            case 2:
                int a = random.nextInt();
                int b = random.nextInt();
                int c = random.nextInt();
                checkSign(a, b, c);
                break;
            case 3:
                int data = random.nextInt();
                selectColor(data);
                break;
            case 4:
                int a1 = random.nextInt();
                int b1 = random.nextInt();
                compareNumbers(a1, b1);
                break;
            case 5:
                int initValue = random.nextInt();
                int delta = random.nextInt();
                boolean increment = random.nextBoolean();
                addOrSubtractAndPrint(initValue, delta, increment);
                break;
        }
    }

    public static void greetings() {
        System.out.println("Hello");
        System.out.println("World");
        System.out.println("from");
        System.out.println("Java");
    }


    public static void checkSign(int a, int b, int c) {
        int sum = a + b + c;
        if (sum >= 0) {
            System.out.println("Сумма положительная, равна: " + sum);
        } else {
            System.out.println("Сумма отрицательная, равна: " + sum);
        }
    }

    public static void selectColor(int data) {
        if (data <= 10) {
            System.out.println("Красный");
        } else if (data <= 20) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers(int a1, int b1) {
        if (a1 >= b1) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        if (increment) {
            initValue += delta;
            System.out.println(initValue);
        } else {
            initValue -= delta;
            System.out.println(initValue);
        }
    }
}