package com.example.redditapp.service;

import com.example.redditapp.model.User;
import com.example.redditapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    public User getUserByUserName(String userName) {
        return userRepo.findByUsername(userName);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public User getUserById(Long userId) {
        return userRepo.findById(userId).get();
    }
}