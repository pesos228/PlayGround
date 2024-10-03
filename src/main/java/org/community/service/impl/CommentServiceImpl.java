package org.community.service.impl;

import org.community.dto.CommentDtoAdd;
import org.community.dto.CommentDtoList;
import org.community.entities.Comment;
import org.community.entities.Discussion;
import org.community.entities.User;
import org.community.exceptions.DiscussionClosedException;
import org.community.exceptions.DiscussionNotFoundException;
import org.community.exceptions.UserNotFoundException;
import org.community.repository.CommentRepository;
import org.community.service.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final DiscussionService discussionService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, DiscussionService discussionService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.discussionService = discussionService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void add(CommentDtoAdd commentDtoAdd) {
        User author = userService.findByEmail(commentDtoAdd.getAuthorEmail());
        if (author == null) {
            throw new UserNotFoundException("User with mail " + commentDtoAdd.getAuthorEmail() + " not found");
        }

        Discussion discussion = discussionService.findById(commentDtoAdd.getDiscussionId());
        if (discussion == null) {
            throw new DiscussionNotFoundException(commentDtoAdd.getDiscussionId());
        }

        if (!discussionService.isDiscussionClosed(discussion.getId())) {
            Comment comment = new Comment(commentDtoAdd.getText(), author, discussion, LocalDateTime.now());
            commentRepository.save(comment);
            discussionService.incrementCommentCount(discussion.getId());
        } else {
            throw new DiscussionClosedException();
        }
    }

    @Override
    public List<CommentDtoList> commentsOfDiscussion(int id) {
        return commentRepository.findAllByDiscussionIdOrderByTime(id).stream()
                .map(comment -> modelMapper.map(comment, CommentDtoList.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findAllByDiscussionIdOrderByTime(int discussionId) {
        return commentRepository.findAllByDiscussionIdOrderByTime(discussionId);
    }
}
