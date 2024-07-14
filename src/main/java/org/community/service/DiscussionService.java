package org.community.service;

import org.community.entities.Community;
import org.community.entities.Discussion;
import org.community.repository.impl.DiscussionRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscussionService {

    private final DiscussionRepositoryImpl discussionRepository;
    private final CommunityService communityService;

    @Autowired
    public DiscussionService(DiscussionRepositoryImpl discussionRepository, CommunityService communityService) {
        this.discussionRepository = discussionRepository;
        this.communityService = communityService;
    }

    public void create(Discussion discussion){
        Community community = communityService.findByGameId(discussion.getCommunity().getGame().getId());
        if (community == null){
            throw new IllegalArgumentException("Community "+ discussion.getCommunity().getGame().getName()+ "doesn't exists");
        }
        discussionRepository.save(discussion);
    }
    public void close(Discussion discussion, int userId){
        if (discussionRepository.existsByCreatorIdAndDiscussionId(userId, discussion.getId()) && discussion.getCloseTime() == null){
            Discussion discussionUpdate = discussionRepository.findById(discussion.getId());
            discussionUpdate.setCloseTime(LocalDateTime.now());
            discussionRepository.save(discussionUpdate);
        }
        else{
            throw new IllegalArgumentException("There is no such discussion or you are not its owner");
        }
    }

    public List<Discussion> searchDiscussions(String name){
        return discussionRepository.findByHeading(name);
    }

    public boolean isDiscussionClosed(int id){
        return discussionRepository.isDiscussionClosed(id);
    }

    public List<Discussion> findUserDiscussions(int id){
        return discussionRepository.findByCreatorId(id);
    }

    public Discussion findById(int id){
        return discussionRepository.findById(id);
    }

    public List<Discussion> listOfDiscussions(){
        return discussionRepository.findAllOrderByCommentDesc();
    }
}
