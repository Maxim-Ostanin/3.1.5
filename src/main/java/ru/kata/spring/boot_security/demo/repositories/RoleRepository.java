package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;


public interface RoleRepository {
    void addRole(Role role);
    List<Role> getAllRoles();

}
