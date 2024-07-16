package org.community.service;
import org.community.dto.GenreDtoName;
import org.community.entities.Genre;
import org.community.exceptions.GenreAlreadyExistsException;
import org.community.repository.impl.GenreRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService extends AbstractService {

    private final GenreRepositoryImpl genreRepository;

    @Autowired
    public GenreService(GenreRepositoryImpl genreRepository, ModelMapper modelMapper) {
        super(modelMapper);
        this.genreRepository = genreRepository;
    }

    @Transactional
    public void save(GenreDtoName genreDtoName){
        Genre genre = convertToEntity(genreDtoName, Genre.class);
        Genre genreTest = genreRepository.findByName(genreDtoName.getName());
        if (genreTest == null){
            genreRepository.save(genre);
        }else {
            throw new GenreAlreadyExistsException(genreDtoName.getName());
        }
    }

    public List<GenreDtoName> findAllByGameId(int id){
        return genreRepository.findAllByGameId(id).stream()
                .map(genre -> convertToDto(genre, GenreDtoName.class))
                .collect(Collectors.toList());
    }

    public List<Genre> findAllByGameName(String name){
        return genreRepository.findAllByGameName(name);
    }

    public Genre findByName(String name){
        return genreRepository.findByName(name);
    }
}
