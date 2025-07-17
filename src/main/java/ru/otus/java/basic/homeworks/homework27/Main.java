package ru.otus.java.basic.homeworks.homework27;

import ru.otus.java.basic.homeworks.homework27.fruits.Apple;
import ru.otus.java.basic.homeworks.homework27.fruits.Orange;

public class Main {
    public static void main(String[] args) {
        Box<Apple> appleBox = new Box<>();
        appleBox.add(new Apple());
        appleBox.add(new Apple());
        appleBox.add(new Apple());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());

        System.out.println("Вес коробки с яблоками: " + appleBox.getWeight());
        System.out.println("Вес коробки с апельсинами: " + orangeBox.getWeight());
        System.out.println("Коробки весят одинаково? " + appleBox.compare(orangeBox));

        Box<Apple> anotherAppleBox = new Box<>();
        System.out.println("\nДо пересыпания:");
        System.out.print("Первоначальная коробка: ");
        appleBox.printContents();
        appleBox.transferTo(anotherAppleBox);
        System.out.println("\nПосле пересыпания:");
        System.out.print("Новая коробка: ");
        anotherAppleBox.printContents();
    }
}

