package org.community.service;

import org.community.dto.CommentDtoAdd;
import org.community.dto.CommentDtoList;
import org.community.dto.DiscussionDto;
import org.community.entities.Comment;
import org.community.entities.Discussion;
import org.community.entities.User;
import org.community.exceptions.DiscussionClosedException;
import org.community.exceptions.DiscussionNotFoundException;
import org.community.exceptions.UserNotFoundException;
import org.community.repository.impl.CommentRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends AbstractService{

    private final CommentRepositoryImpl commentRepository;
    private final DiscussionService discussionService;
    private final UserService userService;


    @Autowired
    public CommentService(CommentRepositoryImpl commentRepository, DiscussionService discussionService, ModelMapper modelMapper, UserService userService) {
        super(modelMapper);
        this.commentRepository = commentRepository;
        this.discussionService = discussionService;
        this.userService = userService;
    }

    @Transactional
    public void add(CommentDtoAdd commentDtoAdd){
        User author = userService.findByEmail(commentDtoAdd.getAuthorEmail());
        if (author == null){
            throw new UserNotFoundException("User with mail "+ commentDtoAdd.getAuthorEmail() + " not found");
        }

        Discussion discussion = discussionService.findById(commentDtoAdd.getDiscussionId());
        if (discussion == null){
            throw new DiscussionNotFoundException(commentDtoAdd.getDiscussionId());
        }

        if (!discussionService.isDiscussionClosed(commentDtoAdd.getDiscussionId())){
            Comment comment = new Comment(commentDtoAdd.getText(), author, discussion, LocalDateTime.now());
            commentRepository.save(comment);
            return;
        }
        throw new DiscussionClosedException();
    }

    public List<CommentDtoList> commentsOfDiscussion(int id){
        return commentRepository.findAllByDiscussionIdOrderByTime(id).stream()
                .map(comment -> convertToDto(comment, CommentDtoList.class))
                .collect(Collectors.toList());
    }
}
