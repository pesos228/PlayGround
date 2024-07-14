package org.community.service;

import org.community.entities.Community;
import org.community.entities.Game;
import org.community.repository.impl.CommunityRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommunityService {
    private final CommunityRepositoryImpl communityRepository;

    @Autowired
    public CommunityService(CommunityRepositoryImpl communityRepository) {
        this.communityRepository = communityRepository;
    }


    public Community findByGameId(int id){
        return communityRepository.findByGameId(id);
    }

    public Community findByGameName(String name){
        return communityRepository.findByGameName(name);
    }

    public List<Community> findAll(){
        return communityRepository.findAllOrderByDiscussionCountDescAndCommentCountDesc();
    }

    @Transactional
    void saveCommunity(Game game) {
        Community community = new Community(game, new ArrayList<>());
        communityRepository.save(community);
    }

}
