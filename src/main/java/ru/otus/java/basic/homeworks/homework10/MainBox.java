package ru.otus.java.basic.homeworks.homework10;

import java.util.Scanner;

public class MainBox {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Введите высоту коробки: ");
        int height = scanner.nextInt();
        System.out.println("Введите ширину коробки: ");
        int length = scanner.nextInt();
        System.out.println("Введите глубину коробки: ");
        int width = scanner.nextInt();
        System.out.println("Введите цвет коробки: ");
        String color = scanner.next();

        Box box = new Box(height, length, width, color);

        while (true) {
            System.out.println("\nВведите необходимую команду из доступных: открыть, закрыть, положить, забрать, " +
                    "перекрасить, инфо, выйти.");
            String cmd = scanner.next().trim().toLowerCase();

            switch (cmd) {
                case "открыть":
                    box.openBox();
                    break;
                case "закрыть":
                    box.closeBox();
                    break;
                case "положить":
                    if (box.isOpen()) {
                        System.out.println("Введите название предмета: ");
                        String item = scanner.next();
                        box.putItem(item);
                    } else {
                        System.out.println("Коробка закрыта!");
                    }
                    break;
                case "забрать":
                    box.takeItem();
                    break;
                case "перекрасить":
                    System.out.println("Введите новый цвет: ");
                    String newColor = scanner.next();
                    box.changeColor(newColor);
                    break;
                case "инфо":
                    box.info();
                    break;
                case "выйти":
                    return;
                default:
                    System.out.println("Неверный ввод.\nПопробуйте еще раз:");
            }
        }
    }
}
