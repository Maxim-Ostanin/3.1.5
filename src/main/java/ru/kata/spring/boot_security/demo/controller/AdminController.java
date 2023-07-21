package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.AdminControllerService;

@Controller
public class AdminController implements AdminControllerInterface {

    private final AdminControllerService adminControllerService;

    @Autowired
    public AdminController(AdminControllerService adminControllerService) {
        this.adminControllerService = adminControllerService;
    }


    @Override
    public String printWelcome(ModelMap model) {
        return adminControllerService.printWelcome(model);
    }

    @Override
    public String showAllUsers(@ModelAttribute("newUser") User newUser, ModelMap model) {
        return adminControllerService.showAllUsers(newUser, model);
    }

    @Override
    public String deleteUser(@PathVariable("id") int id) {
        return adminControllerService.deleteUser(id);
    }

    @Override
    public String create(@ModelAttribute("newUser") User user) {
        return adminControllerService.create(user);
    }

    @Override
    public String updateUser(@ModelAttribute("userEdit") User userEdit, @PathVariable("id") int id) {
        return adminControllerService.updateUser(userEdit, id);
    }

    @Override
    public User findUser(Integer id) {
        return adminControllerService.findUser(id);
    }
}
