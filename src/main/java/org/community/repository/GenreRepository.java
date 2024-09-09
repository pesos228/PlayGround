package org.community.repository;

import org.community.entities.Genre;

import java.util.List;

public interface GenreRepository{
    List<Genre> findAllByGameId(int id);
    List<Genre> findAllByGameName(String name);
    void save(Genre genre);
    Genre findByName(String name);
}
