package org.community.service;

import org.community.entities.Comment;
import org.community.entities.Discussion;
import org.community.entities.User;
import org.community.repository.impl.CommentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepositoryImpl commentRepository;
    private final DiscussionService discussionService;


    @Autowired
    public CommentService(CommentRepositoryImpl commentRepository, DiscussionService discussionService) {
        this.commentRepository = commentRepository;
        this.discussionService = discussionService;
    }

    public void add(String text, User author, Discussion discussion){
        if (!discussionService.isDiscussionClosed(discussion.getId())){
            Comment comment = new Comment(text, author, discussion, LocalDateTime.now());
            commentRepository.save(comment);
        }
        throw new IllegalArgumentException("Discussion is closed");
    }

    public List<Comment> commentsOfDiscussion(int id){
        return commentRepository.findAllByDiscussionId(id);
    }
}
