package ru.otus.java.basic.homeworks.homework27;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }

    public float getWeight() {
        float totalWeight = 0.0f;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        return totalWeight;
    }

    public boolean compare(Box<?> otherBox) {
        return Math.abs(this.getWeight() - otherBox.getWeight()) < 0.0001f;
    }

    public void transferTo(Box<T> targetBox) {
        if (targetBox == this) return; // нельзя пересыпать в себя
        targetBox.fruits.addAll(this.fruits);
        this.fruits.clear();
    }

    public void printContents() {
        System.out.println("Box contains: " + fruits);
    }
}
