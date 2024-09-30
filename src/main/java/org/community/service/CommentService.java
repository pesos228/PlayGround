package org.community.service;

import org.community.dto.CommentDtoAdd;
import org.community.dto.CommentDtoList;
import org.community.entities.Comment;

import java.util.List;

public interface CommentService {

    void add(CommentDtoAdd commentDtoAdd);

    List<CommentDtoList> commentsOfDiscussion(int id);

    List<Comment> findAllByDiscussionIdOrderByTime(int discussionId);
}
