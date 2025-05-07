package ru.otus.java.basic.homeworks.homework11;

import ru.otus.java.basic.homeworks.homework11.Animals.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat("Стеша", 2.7, 1, 100);
        Dog dog = new Dog("Барбос", 2, 1, 100);
        Horse horse = new Horse("Луна", 5, 2, 100);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВведите животное из доступных: кошка, собака, лошадь, никого.");
            String chooseAnimal = scanner.next().trim().toLowerCase();
            Animal current = null;
            switch (chooseAnimal) {
                case "кошка":
                    current = cat;
                    break;
                case "собака":
                    current = dog;
                    break;
                case "лошадь":
                    current = horse;
                    break;
                case "никого":
                    return;
                default:
                    System.out.println("\nНеверный ввод.\nПопробуйте еще раз:");
                    continue;
            }

            boolean forward = true;
        while (forward) {
            System.out.println("\nВведите действие из доступных: бежать, плыть, инфо, выносливость, вернуться, выйти.");
            String chooseAction = scanner.next().trim().toLowerCase();
            switch (chooseAction) {
                case "бежать":
                    current.run();
                    break;
                case "плыть":
                    current.swim();
                    break;
                case "инфо":
                    current.info();
                    break;
                case "выносливость":
                    current.takeStamina();
                    break;
                case "вернуться":
                    forward = false;
                    break;
                case "выйти":
                    return;
                default:
                    System.out.println("\nНеверный ввод.\nПопробуйте еще раз:");
                    continue;
                }
            }
        }
    }
}
