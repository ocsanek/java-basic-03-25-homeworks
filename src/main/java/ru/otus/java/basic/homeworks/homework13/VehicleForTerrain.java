package ru.otus.java.basic.homeworks.homework13;

public class VehicleForTerrain implements Transport {
    private double fuel;

    public VehicleForTerrain(double fuel) {
        this.fuel = fuel;
    }

    @Override
    public boolean move(double distance, Terrain terrain){
        double needed = distance * 1.5;
        if (fuel <  needed) {
            System.out.println("Недостаточно топлива для вездехода. Необходимо: " + needed + ", есть: " + fuel);
            return false;
        }
        fuel -= needed;
        System.out.println("Вездеход проехал " + distance + " по " + terrain + ". Осталось топлива: " + fuel);
        return true;
    }
}
