package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();

        messages.add("Hello!");
        messages.add("To log in press:");
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping("/admin")
    public String showAllUsers(@ModelAttribute("newUser") User newUser, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("user", userService.loadUserByUsername(user.getUsername()));
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        return "admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("admin/{id}")
    public String updateUser(@ModelAttribute("userEdit") User userEdit, @PathVariable("id") int id) {
        if (userEdit.getPassword().equals(userService.getUserById(id).getPassword())) {
            userEdit.setPassword(userEdit.getPassword());
        } else {
            userEdit.setPassword(passwordEncoder.encode(userEdit.getPassword()));
        }
        userService.updateUser(id, userEdit);
        return "redirect:/admin";
    }

    @GetMapping("/findUser")
    @ResponseBody
    public User findUser(Integer id) {
        return userService.getUserById(id);
    }
}
