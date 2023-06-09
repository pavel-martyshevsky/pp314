package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User showUser(Long id);

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User findByUsername(String name);

    User findUserById(Long id);
}
