package org.community.repository;

import org.community.entities.Game;

import java.util.List;

public interface GameRepository{
    List<Game> findAllByGenreIds(List<Integer> ids);
    List<Game> findAllByGenreNames(List<String> names);
    Game findByName(String name);
    List<Game> findAllOrderByFeedBackCountDescAndRatingDesc();
    List<Game> findAllOrderByFeedBackUserRating(int id);
}
