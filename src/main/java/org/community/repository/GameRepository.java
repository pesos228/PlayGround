package org.community.repository;

import org.community.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g JOIN g.genres gen WHERE gen.id = :genreId")
    List<Game> findAllByGenreId(@Param(value = "genreId")int id);
    @Query("SELECT DISTINCT g FROM Game g JOIN g.genres gen WHERE gen.id IN :ids")
    List<Game> findAllByGenreIds(@Param(value = "ids")List<Integer> ids);
    @Query("SELECT g FROM Game g WHERE LOWER(g.name) = LOWER(:name)")
    Game findByName(@Param(value = "name")String name);
    @Query("SELECT g FROM Game g LEFT JOIN g.feedbacks f GROUP BY g.id ORDER BY COUNT(f) DESC")
    List<Game> findAllOrderByFeedbackCountDesc();
    @Query("SELECT g FROM Game g ORDER BY g.rating DESC")
    List<Game> findAllOrderByRatingDesc();
    @Query("SELECT g FROM Game g ORDER BY g.rating ASC")
    List<Game> findAllOrderByRatingAsc();
    @Query("SELECT g FROM Game g LEFT JOIN g.feedbacks f GROUP BY g.id ORDER BY COUNT(f) DESC, g.rating DESC")
    List<Game> findAllOrderByFeedBackCountDescAndRatingDesc();
    @Query("SELECT g FROM Game g LEFT JOIN g.feedbacks f WHERE f.user.id = :userId ORDER BY g.rating DESC")
    List<Game> findAllOrderByFeedBackUserRating(@Param("userId") int id);
}
