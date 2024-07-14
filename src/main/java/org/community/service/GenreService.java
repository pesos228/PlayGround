package org.community.service;
import org.community.entities.Genre;
import org.community.repository.impl.GenreRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepositoryImpl genreRepository;

    @Autowired
    public GenreService(GenreRepositoryImpl genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public void save(Genre genre){
        Genre genreTest = genreRepository.findByName(genre.getName());
        if (genreTest == null){
            genreRepository.save(genre);
        }else {
            throw new IllegalArgumentException("Genre with "+ genre.getName() + " name already exists");
        }
    }

    public List<Genre> findAllByGameId(int id){
        return genreRepository.findAllByGameId(id);
    }

    public List<Genre> findAllByGameName(String name){
        return genreRepository.findAllByGameName(name);
    }
}
