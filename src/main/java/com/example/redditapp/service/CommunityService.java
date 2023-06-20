package com.example.redditapp.service;

import com.example.redditapp.model.Community;
import com.example.redditapp.repository.CommunityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    @Autowired
    CommunityRepo communityRepo;

    public boolean isCommunityNameExists(String name) {
        return communityRepo.existsByCommunityName(name);
    }
    public Community createCommunity(Community community, String communityType) {
        Community communityInfo= new Community();
        if (communityType.equals("private")) {
            communityInfo.setIsPrivate(true);
            communityInfo.setIsRestrict(false);
        }
         else if (communityType.equals("restricted")) {
            communityInfo.setIsPrivate(false);
            communityInfo.setIsRestrict(true);
        }
         else{
             communityInfo.setIsPrivate(false);
             communityInfo.setIsRestrict(false);
        }
        communityInfo.setCommunityName(community.getCommunityName());
        return communityRepo.save(communityInfo);
    }
    public Community updateCommunity(Long id, Community updatedCommunity) {
        Community existingCommunity = communityRepo.findById(id).orElse(null);
        if (existingCommunity != null) {
            existingCommunity.setCommunityName(updatedCommunity.getCommunityName());
            return communityRepo.save(existingCommunity);
        }
        return null;
    }
    public void deleteCommunity(Long id) {
        communityRepo.deleteById(id);
    }
    public Community findCommunity(String communityName) {
        return communityRepo.findByCommunityName(communityName);
    }
    public Community findCommunityById(Long communityId) {
        Community community = communityRepo.findById(communityId).get();
        return community;
    }

    public void saveCommunity(Community community) {
        communityRepo.save(community);
    }
}
