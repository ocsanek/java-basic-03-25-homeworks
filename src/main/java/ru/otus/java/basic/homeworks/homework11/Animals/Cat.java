package ru.otus.java.basic.homeworks.homework11.Animals;

public class Cat extends Animal {
    public Cat(String name, double speedRun, double speedSwim, double stamina) {
        this.name = name;
        this.speedRun = speedRun;
        this.speedSwim = 0;
        this.stamina = stamina;
    }

    @Override
    public double swim(){
        System.out.println(name + " не умеет плавать!");
        return -1;
    }
}
