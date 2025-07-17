package ru.otus.java.basic.homeworks.homework26.service;

import ru.otus.java.basic.homeworks.homework26.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> authenticate(String email, String password);
    boolean isAdmin(int userId);

}
