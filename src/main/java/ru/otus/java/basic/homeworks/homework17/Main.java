package ru.otus.java.basic.homeworks.homework17;

public class Main {
    public static void main(String[] args) {

        PhoneBook pb = new PhoneBook();
        pb.add("Иванов Иван", "8999999999");
        pb.add("Сидоров Леня", "80000000000");
        pb.add("Петров Петр", "89999999991");
        pb.add("Иванов Иван", "89999999992");

        System.out.println("Номера Иванова Ивана: " + pb.find("Иванов Иван"));
        System.out.println("Есть ли номер: 8999999999? " + pb.containsPhoneNumber("8999999999"));
        System.out.println("Есть ли номер: 80000000001? " + pb.containsPhoneNumber("80000000001"));
    }
}
