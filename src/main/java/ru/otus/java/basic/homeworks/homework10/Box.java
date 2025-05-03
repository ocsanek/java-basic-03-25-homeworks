package ru.otus.java.basic.homeworks.homework10;

import java.util.Scanner;

public class Box {
    private final int width;
    private final int height;
    private final int length;
    private String color;
    private boolean isOpen;
    private String item;

    public boolean isOpen() {
        return isOpen;
    }

    public Box(int height, int length, int width, String color) {
        this.width = width;
        this.height = height;
        this.length = length;
        this.color = color;
        this.isOpen = false;
        this.item = null;
        System.out.println("Создана коробка с размерами (ВхШхГ): " + height + "x" + length + "x" + width +
                ", цветом: " + color);
    }

    public void openBox() {
        if (isOpen) {
            System.out.println("Коробка уже открыта");
        } else {
            isOpen = true;
            System.out.println("Коробка открыта");
        }
    }

    public void closeBox() {
        if (!isOpen) {
            System.out.println("Коробка уже закрыта");
        } else {
            isOpen = false;
            System.out.println("Коробка закрыта");
        }
    }

    public void changeColor(String color) {
            this.color = color;
    }

    public void putItem(String item) {
        if (this.isOpen) {
            if (this.item == null) {
                this.item = item;
            } else {
                System.out.println("В коробке уже находится предмет. Это: " + this.item);
            }
        } else {
            System.out.println("Коробка закрыта!");
        }
    }

    public void takeItem() {
         if (isOpen) {
             if (item == null) {
                 System.out.println("В коробке нет предмета!");
             } else {
                 System.out.println("Вытащили из коробки:" + item);
                 item = null;
             }
         } else {
             System.out.println("Коробка закрыта!");
         }
    }

    public void info(){
        System.out.println("Создана коробка с размерами (ВхШхГ):" + height + "x" + length + "x" + width +
            ", цветом: " + color);
        System.out.println("Коробка " + (isOpen ? "открыта" : "закрыта"));
        System.out.println("В коробке лежит: " + item);
    }
}