package ru.otus.java.basic.homeworks.homework13;

public class Human {
    private String name;
    private Transport currentTransport;

    public Human(String name) {
        this.name = name;
    }
    public void mount(Transport transport) {
        if (currentTransport != null) {
            System.out.println(name + " уже находится в транспорте!");
        } else {
            this.currentTransport = transport;
            System.out.println(name + " сел на " + transport.getClass().getSimpleName());
        }
    }

    public void dismount() {
        if (currentTransport == null) {
            System.out.println(name + " и так пешком");
        } else {
            System.out.println(name + " слез с " + currentTransport.getClass().getSimpleName());
            currentTransport = null;
        }
    }

    public boolean move(double distance, Terrain terrain) {
        if (currentTransport != null) {
            boolean result = currentTransport.move(distance, terrain);
            System.out.println(name + (result ? " успешно " : " не ") + "переместился на " + distance + " по " + terrain);
            return result;
        } else {
            System.out.println(name + " прошел пешком " + distance + " по " + terrain);
            return true;
        }
    }
}
