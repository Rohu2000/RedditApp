package com.example.redditapp.service;

import com.example.redditapp.model.Community;
import com.example.redditapp.model.User;
import com.example.redditapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public void addModeratedCommunity(User user, Community community) {
        Set<Community> moderatedCommunities = user.getCommunityModerators();
        moderatedCommunities.add(community);
        user.setCommunityModerators(moderatedCommunities);
        userRepo.save(user);
    }

    public void removeModeratedCommunity(User user, Community community) {
        Set<Community> moderatedCommunities = user.getCommunityModerators();
        moderatedCommunities.remove(community);
        user.setCommunityModerators(moderatedCommunities);
        userRepo.save(user);
    }
}