package org.community.service.impl;

import org.community.dto.GenreDtoName;
import org.community.entities.Genre;
import org.community.exceptions.GenreAlreadyExistsException;
import org.community.repository.GenreRepository;
import org.community.service.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void save(GenreDtoName genreDtoName){
        Genre genre = modelMapper.map(genreDtoName, Genre.class);
        Genre genreTest = genreRepository.findByName(genreDtoName.getName());
        if (genreTest == null){
            genreRepository.save(genre);
        }else {
            throw new GenreAlreadyExistsException(genreDtoName.getName());
        }
    }

    @Override
    public List<GenreDtoName> findAllByGameId(int id){
        return genreRepository.findAllByGameId(id).stream()
                .map(genre -> modelMapper.map(genre, GenreDtoName.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> findAllByGameName(String name){
        return genreRepository.findAllByGameName(name);
    }

    @Override
    public Genre findByName(String name){
        return genreRepository.findByName(name);
    }
}
