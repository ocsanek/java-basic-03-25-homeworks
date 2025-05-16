package ru.otus.java.basic.homeworks.homework13;

public class Car implements Transport {
    private double fuel;

    public Car(double fuel) {
        this.fuel = fuel;
    }

    @Override
    public boolean move(double distance, Terrain terrain){
        if (terrain == Terrain.DENSE_FOREST || terrain == Terrain.SWAMP) {
            System.out.println("Машина не может проехать по " + terrain);
            return false;
        }
        double needed = distance;
        if (fuel <  needed) {
            System.out.println("Недостаточно топлива. Необходимо: " + needed + ", есть: " + fuel);
            return false;
        }
        fuel -= needed;
        System.out.println("Машина проехала " + distance + " по " + terrain + ". Осталось топлива: " + fuel);
        return true;
    }
}
