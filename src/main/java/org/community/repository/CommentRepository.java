package org.community.repository;

import org.community.entities.Comment;

import java.util.List;

public interface CommentRepository{

    List<Comment> findAllByDiscussionIdOrderByTime( int discussionId);
    void save(Comment comment);

}
