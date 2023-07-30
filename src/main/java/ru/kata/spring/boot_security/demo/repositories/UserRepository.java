package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserRepository extends UserDetailsService {

    void addUser(User user);

    void removeUserById(int id);

    List<User> getAllUsers();

    User getUserById(int id);

    void updateUser(int id, User updatedUser);
}
