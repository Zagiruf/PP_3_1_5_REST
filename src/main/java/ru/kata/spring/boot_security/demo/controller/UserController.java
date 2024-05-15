package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService user, RoleRepository roleRepository) {

        this.userService = user;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.gelAllUsers();
        model.addAttribute("users", allUsers);
        return "admin";
    }

    @GetMapping("/admin/id")
    public String show(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "admin";
    }

    @GetMapping("admin/new")
    public String newUser(@ModelAttribute("us") User user) {
        return "new";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("us") User user) {
        userService.saveUser(user);
        return "redirect:admin";
    }

    @GetMapping("/admin/id/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("allRoles",roleRepository.findAll());
        return "edit";
    }


    @PatchMapping("/admin/id")
    public String update(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/id")
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String pageForAuthenticatedUsers(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user1", user);
        return "user";
    }
}
