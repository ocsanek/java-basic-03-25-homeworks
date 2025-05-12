package ru.otus.java.basic.homeworks.homework12;

public class Main {
    public static void main(String[] args) {
        Plate plate = new Plate(100);
        Cat[] cats = {
                new Cat("Барсик", 10),
                new Cat("Мурзик", 10),
                new Cat("Стеша", 5),
                new Cat("Лев", 70),
                new Cat("Тайга", 30),
        };
        for (Cat cat : cats) {
            cat.eat(plate);
        }
        System.out.println("\nПосле кормежки в тарелке осталось: " + plate.getCurrentFoodSize());
        System.out.println("\nСтатус сытости котов:");
        for (Cat cat : cats) {
            System.out.println(cat.toString());
        }
    }
}
