package org.community.service;

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
import org.community.repository.CommentRepository;
import org.community.repository.DiscussionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscussionService extends AbstractService {

    private final DiscussionRepository discussionRepository;
    private final CommunityService communityService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, CommunityService communityService, ModelMapper modelMapper, UserService userService, CommentRepository commentRepository) {
        super(modelMapper);
        this.discussionRepository = discussionRepository;
        this.communityService = communityService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void create(DiscussionDto discussionDto){
        Community community = communityService.findByGameName(discussionDto.getCommunityGameName());
        if (community == null){
            throw new CommunityNotFoundException(discussionDto.getCommunityGameName());
        }
        User user = userService.findByEmail(discussionDto.getCreatorEmail());
        if (user == null){
            throw new UserNotFoundException("User with mail " + discussionDto.getCreatorEmail() +" not found");
        }
        Discussion discussion = convertToEntity(discussionDto, Discussion.class);
        discussion.setCreateTime(LocalDateTime.now());
        discussion.setCreator(user);
        discussion.setCommunity(community);
        discussionRepository.save(discussion);
    }
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

    public List<Discussion> searchDiscussions(String name){
        return discussionRepository.findByHeading(name);
    }

    public boolean isDiscussionClosed(int id){
        return discussionRepository.isDiscussionClosed(id);
    }

    public List<DiscussionDtoMy> findUserDiscussions(int id){
        User user = userService.findById(id);
        if (user == null){
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        List<Discussion> discussions = discussionRepository.findByCreatorId(id);
        return discussions.stream()
                .map(discussion -> {
                    DiscussionDtoMy discussionDtoMy = convertToDto(discussion, DiscussionDtoMy.class);
                    discussionDtoMy.setCommentCount(commentRepository.findAllByDiscussionIdOrderByTime(discussion.getId()).size());
                    return discussionDtoMy;
                })
                .collect(Collectors.toList());
    }

    public Discussion findById(int id){
        return discussionRepository.findById(id);
    }

    public List<DiscussionDtoList> listOfDiscussionsByCommunity(String name){
        Community community = communityService.findByGameName(name);
        if (community == null){
            throw new CommunityNotFoundException(name);
        }
        List<Discussion> discussions = discussionRepository.findAllByCommunityIdAndOrderByTime(community.getId());
        return discussions.stream()
                .map(discussion -> {
                    DiscussionDtoList discussionDtoList = convertToDto(discussion, DiscussionDtoList.class);
                    discussionDtoList.setCommentCount(commentRepository.findAllByDiscussionIdOrderByTime(discussion.getId()).size());
                    return discussionDtoList;
                })
                .collect(Collectors.toList());
    }
}
