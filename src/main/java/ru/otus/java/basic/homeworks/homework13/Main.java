package ru.otus.java.basic.homeworks.homework13;

public class Main {
    public static void main(String[] args) {
        Human petr = new Human("Петр");

        Bicycle bicycle = new Bicycle();
        Car car = new Car(50);
        Horse horse = new Horse(100);
        VehicleForTerrain atv = new VehicleForTerrain(90);

        petr.move(5, Terrain.PLAIN);

        petr.mount(bicycle);
        petr.move(20, Terrain.PLAIN);
        petr.move(20, Terrain.SWAMP);
        petr.dismount();

        petr.mount(car);
        petr.move(20, Terrain.PLAIN);
        petr.move(20, Terrain.SWAMP);
        petr.dismount();

        petr.mount(horse);
        petr.move(20, Terrain.PLAIN);
        petr.move(20, Terrain.SWAMP);
        petr.dismount();

        petr.mount(atv);
        petr.move(20, Terrain.PLAIN);
        petr.move(20, Terrain.SWAMP);
        petr.dismount();
    }
}
