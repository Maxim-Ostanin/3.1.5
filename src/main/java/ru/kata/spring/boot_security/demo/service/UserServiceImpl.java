package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return userRepository.loadUserByUsername(username);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Transactional
    @Override
    public void removeUserById(int id) {
        userRepository.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Transactional
    @Override
    public void updateUser(int id, User updatedUser) {
        userRepository.updateUser(id, updatedUser);
    }

}
