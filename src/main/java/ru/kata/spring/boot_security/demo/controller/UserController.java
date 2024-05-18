package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
    public ModelAndView editUser(@RequestParam("id") Long id) {
        User user = userService.show(id);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);

        List<Role> roles = (List<Role>) roleService.findRoles();
        System.out.println(roles + "***************************************************************************");
        mav.addObject("allRoles", roles);

        return mav;
    }


    @PatchMapping("/admin/id")
 public String update(@ModelAttribute("user") User user) {
//        user.setRoles(role);
        userService.updateUser(user);
        return "redirect:/admin";
    }

//    @GetMapping("/admin/id/role")
//    public String showRole() {
//
//    }
//    @PostMapping("/admin/id")
//    public String roleEdit(@RequestParam("role") List<Role> role) {
//        user.setRoles(role);
//        user.setRoles(roles);
//
//    }




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
