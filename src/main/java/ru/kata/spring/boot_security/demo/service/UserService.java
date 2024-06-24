package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    public List<User> gelAllUsers();

    public User show(Long id);

    public void saveUser(User user);

    public void updateUser(Long id, User updatedUser);

    public void deleteUser(Long id);

    public User findByUsername(String username);
}
