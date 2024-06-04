package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface RoleService {

    public List<Role> findRoles();

    public List<Role> findRolesById(List<Long> roles);

}
