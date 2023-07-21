package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.service.UserControllerService;

@Controller
public class UserController implements UserControllerInterface{

    private final UserControllerService userControllerService;

    @Autowired
    public UserController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @Override
    public String showUser(@PathVariable(value = "id") int id, ModelMap model) {
        return userControllerService.showUser(id, model);
    }

    @Override
    public String showUserforUser(ModelMap model) {
        return userControllerService.showUserforUser(model);
    }
}
