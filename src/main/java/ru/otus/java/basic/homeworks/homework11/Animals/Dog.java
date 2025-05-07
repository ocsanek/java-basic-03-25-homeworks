package ru.otus.java.basic.homeworks.homework11.Animals;

import java.util.Scanner;

public class Dog extends Animal {
    public Dog(String name, double speedRun, double speedSwim, double stamina) {
        this.name = name;
        this.speedRun = speedRun;
        this.speedSwim = speedSwim;
        this.stamina = stamina;
    }

    @Override
    public double swim() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nВведите расстояние для плавания, в метрах: ");
        int distanceForSwim = scanner.nextInt();
        while (distanceForSwim > 0) {
            int staminaCost = distanceForSwim * 2;
            if (stamina < staminaCost) {
                System.out.println("\n" + name + " не может проплыть " + distanceForSwim + "м., устал");
                tired = true;
                return -1;
            } else {
                stamina -= staminaCost;
                double time = distanceForSwim / speedSwim;
                System.out.println("\n" + name + " проплыл " + distanceForSwim + "м., за " + time + " секунд.");
                return time;
            }
        }
        System.out.println("Введите число больше 0");
        return -1;
    }
}