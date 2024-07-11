package org.community.repository;

import org.community.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM Discussion d WHERE d.creator.id = :userId AND d.id = :discussionId")
    boolean existsByCreatorIdAndDiscussionId(@Param(value = "userId") int userId, @Param(value = "discussionId") int discussionId);
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM Discussion d JOIN d.community c WHERE c.game.name = :name")
    boolean existsByCommunityGameName(@Param(value = "name") String name);
    @Query("SELECT CASE WHEN d.closeTime IS NULL THEN FALSE ELSE TRUE END FROM Discussion d WHERE d.id = :discussionId")
    boolean isDiscussionClosed(@Param(value = "discussionId") int discussionId);
    @Query("SELECT d FROM Discussion d WHERE LOWER(d.heading) = LOWER(:name)")
    List<Discussion> findByHeading(@Param(value = "name") String name);
    @Query("SELECT d FROM Discussion d JOIN d.community c WHERE LOWER(c.game.name) = LOWER(:name)")
    List<Discussion> findByCommunityGameName(@Param(value = "name") String name);
    @Query("SELECT d FROM Discussion d JOIN d.community c WHERE c.game.id = :id")
    List<Discussion> findByCommunityGameId(@Param(value = "id") int id);
    @Query("SELECT d FROM Discussion d JOIN d.creator c WHERE c.id = :creatorId")
    List<Discussion> findByCreatorId(@Param(value = "creatorId") int id);
    @Query("SELECT d FROM Discussion d JOIN d.comments c GROUP BY d.id ORDER BY COUNT(c) DESC")
    List<Discussion> findAllOrderByCommentDesc();
}
