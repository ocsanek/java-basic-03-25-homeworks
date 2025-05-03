package ru.otus.java.basic.homeworks.homework10;

public class MainUser {
    public static void main(String[] args) {
        User[] users = new User[]{
                new User("Иванов", "Иван", "Иванович", 1990, "iii@example.com"),
                new User("Петров", "Петро", "Захарович", 1995, "ppz@example.com"),
                new User("Кузнецова", "Ольга", "Васильевна", 1763, "kov@example.com"),
                new User("Шапокляк", "Варвара", "Фаниевна", 2000, "shvf@example.com"),
                new User("Ларионов", "Кирилл", "Шахиевич", 1987, "lkf@example.com"),
                new User("Зинченко", "Зоя", "Константиновна", 1956, "zzk@example.com"),
                new User("Петросян", "Петр", "Петрович", 1945, "ppp@example.com"),
                new User("Лукоян", "Петр", "Петрович", 1986, "lpp@example.com"),
                new User("Захаров", "Дмитрий", "Евгеньевич", 1985, "zde@example.com"),
                new User("Кулич", "Николай", "Николаевич", 1970, "knn@example.com"),
        };
        for (User user : users) {
            if (user.getAge() > 40) {
                user.info();
            }
        }
    }
}