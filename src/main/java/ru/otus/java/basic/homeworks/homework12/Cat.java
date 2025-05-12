package ru.otus.java.basic.homeworks.homework12;

public class Cat {
    private String name;
    private int appetite;
    private boolean isFull = false;


    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public String getName() {
        return name;
    }

    public int getAppetite() {
        return appetite;
    }

    public boolean isFull() {
        return isFull;
    }

    public void eat(Plate plate) {
        if (!isFull && plate.eat(appetite)) {
            isFull = true;
        }
    }

    @Override
    public String toString() {
        return name + " с аппетитом:" + appetite + ". Поел и остался " + (isFull ? "сытый" : "голодный");
    }
}
