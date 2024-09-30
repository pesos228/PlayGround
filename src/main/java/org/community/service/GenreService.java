package org.community.service;

import org.community.dto.GenreDtoName;
import org.community.entities.Genre;

import java.util.List;

public interface GenreService {
    void save(GenreDtoName genreDtoName);
    List<GenreDtoName> findAllByGameId(int id);
    List<Genre> findAllByGameName(String name);
    Genre findByName(String name);
}
