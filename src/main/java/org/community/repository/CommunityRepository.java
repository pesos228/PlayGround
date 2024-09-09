package org.community.repository;

import org.community.entities.Community;

import java.util.List;

public interface CommunityRepository {
    Community findByGameId(int id);
    Community findByGameName(String name);
    List<Community> findAllOrderByDiscussionCountDescAndCommentCountDesc();
    void save(Community community);

}
