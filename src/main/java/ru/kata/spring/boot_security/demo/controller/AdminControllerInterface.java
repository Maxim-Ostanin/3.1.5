package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;

public interface AdminControllerInterface {

    @GetMapping(value = "/")
    String printWelcome(ModelMap model);

    @GetMapping("/admin")
    String showAllUsers(ModelMap model);

    @DeleteMapping("/admin/{id}")
    String deleteUser(@PathVariable("id") int id);

    @GetMapping("admin/new")
    String newUser(@ModelAttribute("user") User user, Model model);

    @PostMapping("admin")
    String create(@ModelAttribute("user") User user);

    @GetMapping("admin/{id}/edit")
    String edit(Model model, @PathVariable("id") int id);

    @PatchMapping("admin/{id}")
    String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id);
}
