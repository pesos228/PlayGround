package org.community.repository;

import org.community.entities.Discussion;

import java.util.List;

public interface DiscussionRepository{
    boolean existsByCreatorIdAndDiscussionId(int userId, int discussionId);
    boolean isDiscussionClosed(int discussionId);
    List<Discussion> findByHeading(String name);
    List<Discussion> findByCreatorId(int id);
    List<Discussion> findAllByCommunityId(int id);
    List<Discussion> findAllByCommunityIdAndOrderByTime(int id);
    void save(Discussion discussion);
    Discussion findById(int id);
    void incrementCommentCount(int discussionId);
    int countByCommunityId(int communityId);
}
