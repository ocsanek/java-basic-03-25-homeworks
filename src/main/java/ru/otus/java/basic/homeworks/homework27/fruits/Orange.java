package ru.otus.java.basic.homeworks.homework27.fruits;

import ru.otus.java.basic.homeworks.homework27.Fruit;

public class Orange extends Fruit {
    @Override
    public float getWeight() {
        return 1.5f; // например, 1.5 единицы
    }

    @Override
    public String toString() {
        return "Orange";
    }
}
