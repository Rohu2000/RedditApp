package com.example.redditapp.repository;

import com.example.redditapp.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepo extends JpaRepository<Community, Long> {

     Community findByCommunityName(String communityName);

    boolean existsByCommunityName(String communityName);
}
