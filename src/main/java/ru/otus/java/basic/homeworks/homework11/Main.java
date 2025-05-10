package ru.otus.java.basic.homeworks.homework11;

import ru.otus.java.basic.homeworks.homework11.animals.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Стеша", 2.7, 1, 100);
        Dog dog = new Dog("Барбос", 2, 1, 100);
        Horse horse = new Horse("Луна", 5, 2, 100);

        cat.run(10);
        dog.run(10);
        horse.run(10);
        cat.swim(10);
        dog.swim(10);
        horse.swim(10);
        cat.info();
        dog.info();
        horse.info();
    }
}
