package ru.otus.java.basic.homeworks.homework13;

public class Bicycle implements Transport {

    @Override
    public boolean move(double distance, Terrain terrain){
        if (terrain == Terrain.SWAMP) {
            System.out.println("Велосипед не может проехать по болоту");
            return false;
        }
        System.out.println("Велосипед проехал " + distance + " по " + terrain + ".");
        return true;
    }
}
