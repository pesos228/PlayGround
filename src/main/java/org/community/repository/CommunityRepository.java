package org.community.repository;

import org.community.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer> {
    @Query("SELECT c FROM Community c WHERE c.game.id = :gameId")
    Community findByGameId(@Param(value = "gameId")int id);
    @Query("SELECT c FROM Community c WHERE LOWER(c.game.name) = LOWER(:name)")
    Community findByGameName(@Param(value = "name")String name);
    @Query("SELECT c FROM Community c LEFT JOIN c.discussions d LEFT JOIN d.comments com GROUP BY c.id ORDER BY COUNT(com) DESC")
    List<Community> findAllOrderByCommentCountDesc();
    @Query("SELECT c FROM Community c LEFT JOIN c.discussions d GROUP BY c.id ORDER BY COUNT(d) DESC")
    List<Community> findAllOrderByDiscussionCountDesc();
    @Query("SELECT c FROM Community c LEFT JOIN c.discussions d LEFT JOIN d.comments com GROUP BY c.id ORDER BY COUNT(d) DESC, COUNT(com) DESC")
    List<Community> findAllOrderByDiscussionCountDescAndCommentCountDesc();

}
