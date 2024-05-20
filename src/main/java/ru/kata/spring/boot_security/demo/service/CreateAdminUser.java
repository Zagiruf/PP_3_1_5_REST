package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateAdminUser {
    private UserRepository userRepository;

    @Autowired
    public CreateAdminUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void saveUserAdmin() {
        User userAdmin = new User("admin", "$2a$12$x6nbj9VSXa6CoMzfocg3vewP6WTRoKB6h6bKtnm4JpO5gDzJMgQz.", 26);
        User userUser = new User("user", "$2a$12$CjuQIBDQNbakJ6XXyQCot.TN2Rg1XT2mU2f3T9DoHmZy3qo3JhGIa", 35);
        List<Role> roleAdmin = new ArrayList<>();
        roleAdmin.add(new Role("ROLE_ADMIN"));
        List<Role> roleUser = new ArrayList<>();
        userAdmin.setRoles(roleAdmin);
        roleUser.add(new Role("ROLE_USER"));
        userUser.setRoles(roleUser);
        userRepository.save(userAdmin);
        userRepository.save(userUser);
    }
}

