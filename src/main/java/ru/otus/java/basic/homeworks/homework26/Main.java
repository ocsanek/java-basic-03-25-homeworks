package ru.otus.java.basic.homeworks.homework26;

import ru.otus.java.basic.homeworks.homework26.entity.User;
import ru.otus.java.basic.homeworks.homework26.service.UserService;
import ru.otus.java.basic.homeworks.homework26.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        Optional<User> user = userService.authenticate(email, password);
        if (user.isPresent()) {
            System.out.println("Успешный вход!");
            System.out.println("Пользователь: " + user.get());
            if (userService.isAdmin(user.get().getId())) {
                System.out.println("Пользователь является администратором.");
            } else {
                System.out.println("Пользователь НЕ является администратором.");
            }
        } else {
            System.out.println("Неверный email или пароль.");
        }
    }
}
