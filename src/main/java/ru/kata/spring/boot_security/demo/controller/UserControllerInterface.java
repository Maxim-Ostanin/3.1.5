package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface UserControllerInterface {

    @GetMapping("/user/{id}")
    String showUser(@PathVariable(value = "id") int id, ModelMap model);

    @GetMapping("/user")
    String showUserforUser(ModelMap model);
}
