package ru.otus.java.basic.homeworks.homework13;

public class Horse implements Transport {
    private double stamina;

    public Horse(double stamina) {
        this.stamina = stamina;
    }

    @Override
    public boolean move(double distance, Terrain terrain){
        if (terrain == Terrain.SWAMP) {
            System.out.println("Лошадь не может пройти через болото");
            return false;
        }
        double needed = distance * 2;
        if (stamina <  needed) {
            System.out.println("Лошади не хватает сил. Необходимо: " + needed + ", есть: " + stamina);
            return false;
        }
        stamina -= needed;
        System.out.println("Лошадь прошла " + distance + " по " + terrain + ". Осталось сил: " + stamina);
        return true;
    }
}
