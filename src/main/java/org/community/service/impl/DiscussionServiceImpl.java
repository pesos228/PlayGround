package org.community.service.impl;

import org.community.dto.DiscussionDto;
import org.community.dto.DiscussionDtoClose;
import org.community.dto.DiscussionDtoList;
import org.community.dto.DiscussionDtoMy;
import org.community.entities.Community;
import org.community.entities.Discussion;
import org.community.entities.User;
import org.community.exceptions.CommunityNotFoundException;
import org.community.exceptions.DiscussionNotFoundException;
import org.community.exceptions.DiscussionNotOwnedException;
import org.community.exceptions.UserNotFoundException;
import org.community.repository.DiscussionRepository;
import org.community.service.CommunityService;
import org.community.service.DiscussionService;
import org.community.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final ModelMapper modelMapper;
    private final CommunityService communityService;
    private final UserService userService;

    @Autowired
    public DiscussionServiceImpl(DiscussionRepository discussionRepository, ModelMapper modelMapper, CommunityService communityService, UserService userService) {
        this.discussionRepository = discussionRepository;
        this.modelMapper = modelMapper;
        this.communityService = communityService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void create(DiscussionDto discussionDto) {
        Community community = communityService.findByGameName(discussionDto.getCommunityGameName());
        if (community == null) {
            throw new CommunityNotFoundException(discussionDto.getCommunityGameName());
        }
        User user = userService.findByEmail(discussionDto.getCreatorEmail());
        if (user == null) {
            throw new UserNotFoundException("User with mail " + discussionDto.getCreatorEmail() + " not found");
        }
        Discussion discussion = modelMapper.map(discussionDto, Discussion.class);
        discussion.setCreateTime(LocalDateTime.now());
        discussion.setCreator(user);
        discussion.setCommunity(community);
        discussionRepository.save(discussion);
        communityService.incrementDiscussionCount(community.getId());
    }

    @Override
    @Transactional
    public void close(DiscussionDtoClose discussionDtoClose) {
        Discussion discussion = discussionRepository.findById(discussionDtoClose.getId());
        if (discussion == null) {
            throw new DiscussionNotFoundException(discussionDtoClose.getId());
        }

        User user = userService.findById(discussionDtoClose.getCreatorId());
        if (user == null) {
            throw new UserNotFoundException("User with ID " + discussionDtoClose.getCreatorId() + " not found");
        }

        if (discussionRepository.existsByCreatorIdAndDiscussionId(discussionDtoClose.getCreatorId(), discussionDtoClose.getId()) && discussion.getCloseTime() == null) {
            discussion.setCloseTime(LocalDateTime.now());
            discussionRepository.save(discussion);
        } else {
            throw new DiscussionNotOwnedException("There is no such discussion or you are not its owner");
        }
    }

    @Override
    public List<Discussion> searchDiscussions(String name){
        return discussionRepository.findByHeading(name);
    }

    @Override
    public boolean isDiscussionClosed(int id) {
        Discussion discussion = discussionRepository.findById(id);
        return discussion != null && discussion.getCloseTime() != null;
    }

    @Override
    public List<DiscussionDtoMy> findUserDiscussions(int id){
        User user = userService.findById(id);
        if (user == null){
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        List<Discussion> discussions = discussionRepository.findByCreatorId(id);
        return discussions.stream()
                .map(discussion -> {
                    DiscussionDtoMy discussionDtoMy = modelMapper.map(discussion, DiscussionDtoMy.class);
                    discussionDtoMy.setCommentCount(discussion.getComments().size());
                    return discussionDtoMy;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Discussion findById(int id){
        return discussionRepository.findById(id);
    }

    @Override
    public List<DiscussionDtoList> listOfDiscussionsByCommunity(String name){
        Community community = communityService.findByGameName(name);
        if (community == null){
            throw new CommunityNotFoundException(name);
        }
        List<Discussion> discussions = discussionRepository.findAllByCommunityIdAndOrderByTime(community.getId());
        return discussions.stream()
                .map(discussion -> {
                    DiscussionDtoList discussionDtoList = modelMapper.map(discussion, DiscussionDtoList.class);
                    discussionDtoList.setCommentCount(discussion.getComments().size());
                    return discussionDtoList;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Discussion> findAllByCommunityId(int id) {
        return discussionRepository.findAllByCommunityId(id);
    }

    @Override
    public int getDiscussionCountForCommunity(int communityId) {
        return discussionRepository.countByCommunityId(communityId);
    }

    @Override
    public void incrementCommentCount(int discussionId) {
        discussionRepository.incrementCommentCount(discussionId);
    }
}
