package ru.otus.java.basic.homeworks.homework11.Animals;

import java.util.Scanner;

public abstract class Animal {
    String name;
    double speedRun;
    double speedSwim;
    double stamina;
    boolean tired = false;


    Scanner scanner = new Scanner(System.in);

    public double run() {
        System.out.println("\nВведите расстояние для пробежки, в метрах: ");
        int distanceForRun = scanner.nextInt();
        while (distanceForRun > 0) {
            int staminaCost = distanceForRun;
            if (stamina < staminaCost) {
                System.out.println("\n" + name + " не может пробежать " + distanceForRun + "м., устал");
                tired = true;
                return -1;
            } else {
                stamina -= staminaCost;
                double time = distanceForRun / speedRun;
                System.out.printf("\n%s пробежал %d м., за %.2f секунд.", name, distanceForRun, time);
                return time;
            }
        }
        System.out.println("Введите число больше 0");
        return -1;
    }

    public abstract double swim();

    public void takeStamina() {
        System.out.println("\nВведите количество выносливости которое хотите добавить: ");
        int size = scanner.nextInt();
        if (size >= 0 && size <= 1000) {
            this.stamina += size;
        } else {
            System.out.println("\nВы ввели " + ((size < 0) ? "выносливость меньше ноля" : "больше 1000 ед." +
                    ", исправьте значение!"));
        }
    }

    public void info() {
        System.out.println("\nИнформация о " + name + ": ");
        System.out.println("Скорость бега: " + speedRun);
        System.out.println("Скорость плавания: " + speedSwim);
        System.out.println("Остаток выносливости: " + stamina);
        System.out.println("Устал: " + (tired ? "да" : "нет"));
    }
}