package ru.otus.java.basic.homeworks.homework10;

public class User {
    private String lastname;
    private String name;
    private String surname;
    private int birthdayYear;
    private String email;

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public String getEmail() {
        return email;
    }

    public User(String lastname, String name, String surname, int birthdayYear, String email) {
            this.lastname = lastname;
            this.name = name;
            this.surname = surname;
            this.birthdayYear = birthdayYear;
            this.email = email;
    }

    public int getAge() {
        int currentYear = 2025;
        return (currentYear - birthdayYear);
    }

    public void info() {
        System.out.println("ФИО:" + lastname + " " + name + " " + surname);
        System.out.println("Год рождения: " + birthdayYear);
        System.out.println("e-mail: " + email);
        System.out.println();
    }
}
