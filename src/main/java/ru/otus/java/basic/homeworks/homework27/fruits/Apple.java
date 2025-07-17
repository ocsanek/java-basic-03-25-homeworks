package ru.otus.java.basic.homeworks.homework27.fruits;

import ru.otus.java.basic.homeworks.homework27.Fruit;

public class Apple extends Fruit {
    @Override
    public float getWeight() {
        return 1.0f; // например, 1.0 единица
    }

    @Override
    public String toString() {
        return "Apple";
    }
}
