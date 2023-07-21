package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Component
public class RunAfterStartup {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RunAfterStartup(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    Role adminRole = new Role("ROLE_ADMIN");
    Role userRole = new Role("ROLE_USER");
    Set<Role> adminAndUserRoles = Set.of(adminRole, userRole);
    Set<Role> userRoles = Set.of(userRole);
    Set<Role> adminRoles = Set.of(adminRole);
    User admin = new User("admin", "admin", 20,
            "$2a$12$JYsSVSSCOoRClGD6eEeCxOIla0OaWqS3GL4mjNdGmpNA69YSUmiLq", adminAndUserRoles); // password - "admin"
    User user = new User("user", "user", 35,
            "$2a$12$rFdTI7eHrwj5cy5hVjMjUOAWJfjbyKrMyfDB/wJsCj5qKgFOVnDwS", userRoles); // password - "user"
    User test = new User("test", "test", 50,
            "$2a$12$PF3tK6n8FTE82mUcyR9j.eyG04jOmX2adiMDKcp8x8wFIy4hZUfWe", adminRoles); // password - "test"


    public void addUser(User user) {
        userService.addUser(user);
    }

    public void addRole(Role role) {
        roleService.addRole(role);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        addRole(adminRole);
        addRole(userRole);
        addUser(admin);
        addUser(user);
        addUser(test);
    }
}