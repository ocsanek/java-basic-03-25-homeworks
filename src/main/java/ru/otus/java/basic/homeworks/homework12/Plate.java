package ru.otus.java.basic.homeworks.homework12;

public class Plate {
    private final int maxFoodSize;
    private int currentFoodSize;
    private boolean full = false;
    private boolean eat = false;

    public Plate(int size) {
        this.maxFoodSize = size;
        this.currentFoodSize = size;
        this.full = true;
    }

    public int getMaxFoodSize() {
        return maxFoodSize;
    }

    public int getCurrentFoodSize() {
        return currentFoodSize;
    }

    public boolean isFull() {
        return full;
    }

    public void addFood(int amount){
        if (amount <= 0 && (amount + currentFoodSize <= maxFoodSize) ) {
            System.out.println("Неверное количество добавленной еды");
        }
        currentFoodSize = Math.min(currentFoodSize + amount, maxFoodSize);
    }

    public boolean eat(int amount){
        if (amount > currentFoodSize) {
            System.out.println("Не хватает еды");
        }
        if (currentFoodSize >= amount) {
            currentFoodSize -= amount;
            return true;
        } else {
            return false;
        }
    }
}
