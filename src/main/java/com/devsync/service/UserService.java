package com.devsync.service;

import com.devsync.model.User;
import com.devsync.repository.UserRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService(EntityManagerFactory emf) {
        this.userRepository = new UserRepository(emf);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    }
