package org.community.repository;

import org.community.entities.Discussion;

import java.util.List;

public interface DiscussionRepository{
    boolean existsByCreatorIdAndDiscussionId(int userId, int discussionId);
    boolean isDiscussionClosed(int discussionId);
    List<Discussion> findByHeading(String name);
    List<Discussion> findByCreatorId(int id);
    List<Discussion> findAllOrderByCommentDesc();
}
