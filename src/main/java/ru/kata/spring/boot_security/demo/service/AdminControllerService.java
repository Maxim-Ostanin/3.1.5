package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminControllerService {

    private final UserRepository userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminControllerService(@Qualifier("userRepositoryImpl") UserRepository userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();

        messages.add("Hello!");
        messages.add("To log in press:");
        model.addAttribute("messages", messages);
        return "index";
    }

    public String showAllUsers(@ModelAttribute("newUser") User newUser, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("user", userService.loadUserByUsername(user.getUsername()));
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin";
    }

    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    public String create(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    public String updateUser(@ModelAttribute("userEdit") User userEdit, @PathVariable("id") int id) {
        if (userEdit.getPassword().equals(userService.getUserById(id).getPassword())) {
            userEdit.setPassword(userEdit.getPassword());
        } else {
            userEdit.setPassword(passwordEncoder.encode(userEdit.getPassword()));
        }
        userService.updateUser(id, userEdit);
        return "redirect:/admin";
    }

    public User findUser(Integer id) {
        return userService.getUserById(id);
    }

}
