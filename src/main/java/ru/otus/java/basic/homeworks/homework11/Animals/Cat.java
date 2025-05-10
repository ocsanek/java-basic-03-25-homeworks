package ru.otus.java.basic.homeworks.homework11.animals;

public class Cat extends Animal {
    public Cat(String name, double speedRun, double speedSwim, double stamina) {
        this.name = name;
        this.speedRun = speedRun;
        this.speedSwim = 0;
        this.stamina = stamina;
    }

    @Override
    public double swim(double distanceForSwim) {
        System.out.println("\n" + name + " плавать не умеет!");
        return -1;
    }
}
