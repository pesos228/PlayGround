package org.community.repository;

import org.community.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    @Query("SELECT f FROM Feedback f JOIN f.game g WHERE g.id = :gameId")
    List<Feedback> findAllByGameId(@Param(value = "gameId")int id);
    @Query("SELECT f FROM Feedback f JOIN f.user u WHERE u.id = :userId")
    List<Feedback> findAllByUserId(@Param(value = "userId")int id);
    @Query("SELECT f FROM Feedback f JOIN f.user u JOIN f.game g WHERE u.id = :userId AND g.id = :gameId")
    Feedback findByGameIdAndUserId(@Param(value = "userId")int userId, @Param(value = "gameId")int gameId);
}
