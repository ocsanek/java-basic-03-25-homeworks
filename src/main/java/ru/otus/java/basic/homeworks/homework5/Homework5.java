package ru.otus.java.basic.homeworks.homework5;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Homework5 {
    public static void main(String[] args) {
        System.out.println("\nЗадание №1");
        printNString(2, "Hello, world!");

        System.out.println("\nЗадание №2");
        int[] a = {1, 5, 7, 2, 10, 12, 4, 16};
        sumMoreThanFive(a);

        System.out.println("\nЗадание №3");
        changeIntInArray(3, 2,1,2,5,6,6,7,43);

        System.out.println("\nЗадание №4");
        int[] b = new int[]{10, 5, 2, 4, 1, 6, -10};
        plusIntForArray(4, b);

        System.out.println("\nЗадание №5");
        firstOrSecondHalf(1,2,1,3,1,2,3,5,4,1,1,2,3,1);

        System.out.println("\nЗадание №6");
        int[] c = {6,2,6};
        sumArrays(a,b,c);

        System.out.println("\nЗадание №7");
        dotInArray(1,2,3,2,10,2,7,9,5,4,3,6);

        System.out.println("\nЗадание №8");
        changePosInArray(1,2,3,2,10,2,7,9,5,4,3,6);

        System.out.println("\nЗадание №8");
        reverseArray(1, 2, 3, 4, 5, 10, 12, -8);
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

    public static void sumArrays(int[] a, int[] b, int[] c) {

        int longest = a.length;
        if (b.length > longest) {
            longest = b.length;
        }
        if (c.length > longest) {
            longest = c.length;
        }
        int[] sumArr = new int[longest];
        for (int i = 0; i < a.length; i++) {
            sumArr[i] += a[i];
        }
        for (int i = 0; i < b.length; i++) {
            sumArr[i] += b[i];
        }
        for (int i = 0; i < c.length; i++) {
            sumArr[i] += c[i];
        }
        System.out.print("Массив \"a\": ");
        System.out.println(Arrays.toString(a));
        System.out.print("Массив \"b\": ");
        System.out.println(Arrays.toString(b));
        System.out.print("Массив \"c\": ");
        System.out.println(Arrays.toString(c));
        System.out.print("Сумма всех массивов: ");
        System.out.println(Arrays.toString(sumArr));

    }

    public static void dotInArray(int...dotArr) {
        int sumLeft = 0;
        int sumRight = 0;
        int sumArray = 0;
        int dotIndex = 0;
        int[] finalArray;

        for (int i = 0; i < dotArr.length; i++) {
            sumArray += dotArr[i];
        }

        for (int i = 0; i < dotArr.length; i++) {
            sumLeft += dotArr[i];
            sumRight = sumArray - sumLeft;
            if (sumLeft == sumRight) {
                dotIndex = i;
                break;
            }
        }

        finalArray = new int[dotArr.length +1];

        for (int i = 0; i <= dotIndex; i++) {
            finalArray[i] = dotArr[i];
        }

        finalArray[dotIndex + 1] = 0;

        for (int i = dotIndex + 1; i < dotArr.length; i++) {
            finalArray[i + 1] = dotArr[i];
        }

        System.out.print("Массив: ");
        System.out.println(Arrays.toString(dotArr));

        if (sumLeft != sumRight) {
            System.out.println("НЕТ СЕРЕДИНЫ МАССИВА");
        } else {
            System.out.print("Индекс середины массива: ");
            System.out.println(dotIndex);
            System.out.print("Массив с отметкой середины: ");
            System.out.println(Arrays.toString(finalArray));
        }
    }

    public static void changePosInArray(int...changeArr) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите как отсортировать массив:");
        System.out.println("По возрастанию - 0");
        System.out.println("По убыванию - 1");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0:
                Arrays.sort(changeArr);
                System.out.println(Arrays.toString(changeArr));
                break;
            case 1:
                Integer[] changeArr1 = new Integer[changeArr.length];
                for (int i = 0; i < changeArr.length; i++) {
                    changeArr1[i] = changeArr[i];
                }
                Arrays.sort(changeArr1, Collections.reverseOrder());
                System.out.println(Arrays.toString(changeArr1));
        }
    }

    public static void reverseArray(int...revArr) {
        System.out.println("Массив в первоначальном виде: ");
        System.out.println(Arrays.toString(revArr));

        int[] revArr1 = new int[revArr.length];
        for (int i = 0; i < revArr.length; i++) {
            revArr1[i] = revArr[(revArr.length - 1) - i];
        }
        System.out.println("Массив после: ");
        System.out.println(Arrays.toString(revArr1));
    }
}
