package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> gelAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User show(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        List<Role> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
                roles.add(existingRole);
            }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.getById(id);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setPassword(updatedUser.getPassword());

        List<Role> roles = new ArrayList<>();
        for (Role role : updatedUser.getRoles()) {
            Role existingRole = roleRepository.getById(role.getId());
            roles.add(existingRole);
        }
        existingUser.setRoles(roles);

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found ", username));
        }
        return user;
    }
}
