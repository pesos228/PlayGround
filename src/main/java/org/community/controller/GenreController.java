package org.community.controller;

import org.community.dto.GenreDtoName;
import org.community.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public void addGenre(@RequestBody GenreDtoName genreDtoName){
        genreService.save(genreDtoName);
    }

    @GetMapping("/{gameId}")
    public List<GenreDtoName> listGenreOfGame(@PathVariable int gameId){
        return genreService.findAllByGameId(gameId);
    }

}
