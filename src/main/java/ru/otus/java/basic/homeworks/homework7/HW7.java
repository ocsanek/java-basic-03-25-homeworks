package ru.otus.java.basic.homeworks.homework7;

import java.awt.image.AffineTransformOp;
import java.util.Arrays;
import java.util.Random;

public class HW7 {

    public static void main(String[] args) {
        System.out.println("\nЗадание №1");
        int[][] data = {{-1,-2,-3},
                        {-24,5,6},
                        {7,8,9}};
        int result = sumOfPositiveElements(data);
        System.out.println(result);


        System.out.println("\n\nЗадание №2");
        printSquare(4);

        System.out.println("\n\nЗадание №3");
        char[][] arr2d = new char[6][6];

        System.out.println("До обнуления диагоналей:");
        printDiagonal(arr2d);
        nullForDiagonal(arr2d);

        System.out.println("\nПосле обнуления диагоналей:");
        printDiagonal(arr2d);


        System.out.println("\n\nЗадание №4");
        int[][] arr = new int[10][14];
        int max = findMax(arr);
        System.out.println("\nМаксимальный элемент массива: " + max);


        System.out.println("\n\nЗадание №5");
        int sum = sumForSecondArr(arr);
        System.out.println("\nCумма элементов второй строки массива равна: " + sum);
    }

    public static int sumOfPositiveElements(int[][] arrSum) {
        int sum = 0;
        for (int i = 0; i < arrSum.length; i++) {
            if (arrSum[i] == null) {
                continue;
            }
            for (int j = 0; j < arrSum[i].length; j++) {
                if (arrSum[i][j] > 0) {
                    sum += arrSum[i][j];
                }
            }
        }
        return sum;
    }


    public static void printSquare(int size) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%3s", "*");
            }
            System.out.println();
        }
    }

    public static void printDiagonal(char[][] arr2) {
        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < arr2[i].length; j++) {
                if (arr2[i][j] == '0') {
                    System.out.printf("%3s", arr2[i][j]);
                } else {
                    System.out.printf("%3s", "!");
                }
            }
            System.out.println();
        }
    }

    public static void nullForDiagonal(char[][] arr2) {

        for (int i = 0; i < arr2.length; i++){
            if (i < arr2.length) {
                arr2[i][i] = '0';
            }
            int j = arr2[i].length - i - 1;
            if (j >= 0 && j < arr2[i].length) {
                arr2[i][j] = '0';
            }
        }
    }

    public static int findMax(int[][] array) {
        int maxNumber = 0;
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++){
                array[i][j] = random.nextInt(90) - 10;

                if (array[i][j] > maxNumber) {
                    maxNumber = array[i][j];
                }
            }
        }
        return maxNumber;
    }

    public static int sumForSecondArr(int[][] arr) {


        if (arr == null || arr.length < 2 || arr[1] == null) return -1;

        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = random.nextInt(20) - 10;
            }
        }

        int secondSum = 0;
        for (int i = 0; i < arr[1].length; i++) {
            secondSum += arr[1][i];
        }
        return secondSum;
    }
}