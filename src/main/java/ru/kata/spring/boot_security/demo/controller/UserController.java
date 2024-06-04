package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, @ModelAttribute("user") User user, Principal principal) {
        User userPrincipal = userService.findByUsername(principal.getName());
        model.addAttribute("user1", userPrincipal);
        List<User> allUsers = userService.gelAllUsers();
        model.addAttribute("users", allUsers);
        model.addAttribute("allRoles", roleService.findRoles());
        return "admin";
    }

//    @GetMapping("/admin/{id}")
//    public String show(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("user", userService.show(id));
//        return "admin";
//    }

//    @GetMapping("admin/new")
//    public ModelAndView newUser(@ModelAttribute("user") User user) {
//        ModelAndView mav = new ModelAndView("new");
//        mav.addObject("allRoles", roleService.findRoles());
//        return mav;
//    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleId) {
        user.setRoles(roleService.findRolesById(roleId));
        userService.saveUser(user);
        return "redirect:/admin";
    }

//    @GetMapping("/admin/{id}/edit")
//    public ModelAndView editUser(@PathVariable("id") Long id) {
//        ModelAndView mav = new ModelAndView("edit");
//        mav.addObject("user", userService.show(id));
//        mav.addObject("allRoles", roleService.findRoles());
//        return mav;
//    }


    @PatchMapping("/admin")
    public String update(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        user.setRoles(roleService.findRolesById(roleIds));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin")
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
