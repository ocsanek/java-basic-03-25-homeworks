package ru.otus.java.basic.homeworks.homework5;

import java.util.Arrays;

public class Homework5 {
    public static void main(String[] args) {
        printNString(2, "Hello, world!");

        int[] a = {1, 5, 7, 2, 10, 12, 4, 16};
        sumMoreThanFive(a);

        changeIntInArray(3, 2,1,2,5,6,6,7,43);

        int[] b = new int[]{10, 5, 2, 4, 1, 6, -10};
        plusIntForArray(4, b);

        firstOrSecondHalf(1,2,1,3,1,2,3,5,4,1,1,2,3,1);
    }

    public static void printNString(int a, String text) {
        for (int i = 0; i < a; i++) {
            System.out.println(text);
        }
    }

    public static void sumMoreThanFive(int[] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++)
            if (a[i] > 5) {
                sum += a[i];
            }
        System.out.println(sum);
    }

    public static void changeIntInArray(int a, int...arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = a;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void plusIntForArray(int c, int[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] += c;
        }
        System.out.println(Arrays.toString(b));
    }

    public static void firstOrSecondHalf(int...arr) {
        int sumLeft = 0;
        int sumRight = 0;

        for (int i = 0; i < arr.length / 2; i++) {
            sumLeft += arr[i];
        }
        for (int i = arr.length / 2; i < arr.length; i++) {
            sumRight += arr[i];
        }
        if (sumLeft > sumRight) {
            System.out.println("Левая часть " + sumLeft + " больше правой " + sumRight);
        } else if (sumLeft < sumRight) {
            System.out.println("Левая часть " + sumLeft + " меньше правой " + sumRight);
        } else {
            System.out.println("Левая часть " + sumLeft + " равна правой " + sumRight);
        }
    }
}
