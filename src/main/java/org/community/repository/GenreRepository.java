package org.community.repository;

import org.community.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query("SELECT gr FROM Genre gr JOIN gr.games g WHERE g.id = :gameId")
    List<Genre> findAllByGameId(@Param(value = "gameId")int id);
    @Query("SELECT gr FROM Genre gr JOIN gr.games g WHERE LOWER(g.name) = LOWER(:gameName)")
    List<Genre> findAllByGameName(@Param(value = "gameName")String name);
}
