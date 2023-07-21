package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;

public interface AdminControllerInterface {

    @GetMapping(value = "/")
    String printWelcome(ModelMap model);

    @GetMapping("/admin")
    String showAllUsers(@ModelAttribute("newUser") User newUser, ModelMap model);

    @DeleteMapping("/admin/{id}")
    String deleteUser(@PathVariable("id") int id);

    @PostMapping("/admin")
    String create(@ModelAttribute("newUser") User newUser);

    @PatchMapping("admin/{id}")
    String updateUser(@ModelAttribute("userEdit") User userEdit, @PathVariable("id") int id);

    @GetMapping("/findUser")
    @ResponseBody
    User findUser(Integer id);
}
