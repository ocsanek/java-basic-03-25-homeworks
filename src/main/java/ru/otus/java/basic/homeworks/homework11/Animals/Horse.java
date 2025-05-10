package ru.otus.java.basic.homeworks.homework11.animals;

import java.util.Scanner;

public class Horse extends Animal {
    public Horse(String name, double speedRun, double speedSwim, double stamina) {
        this.name = name;
        this.speedRun = speedRun;
        this.speedSwim = speedSwim;
        this.stamina = stamina;
    }

    @Override
    public double swim(double distanceForSwim) {
        while (distanceForSwim > 0) {
            double staminaCost = distanceForSwim * 4;
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
