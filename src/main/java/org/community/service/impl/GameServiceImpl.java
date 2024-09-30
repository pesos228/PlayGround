package org.community.service.impl;

import org.community.dto.GameDtoList;
import org.community.dto.GameDtoName;
import org.community.dto.GameDtoRegister;
import org.community.dto.GenreDtoName;
import org.community.entities.Game;
import org.community.entities.Genre;
import org.community.exceptions.FeedbackNotFoundException;
import org.community.exceptions.GameAlreadyExistsException;
import org.community.exceptions.GenreNotFoundException;
import org.community.repository.GameRepository;
import org.community.service.CommunityService;
import org.community.service.GameService;
import org.community.service.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CommunityService communityService;

    @Override
    @Transactional
    public void save(GameDtoRegister gameDtoRegister){
        Game game = modelMapper.map(gameDtoRegister, Game.class);
        Game gameTest = gameRepository.findByName(game.getName());
        if (gameTest == null){
            Set<Genre> genres = gameDtoRegister.getGenres().stream()
                    .map(genreDtoName -> {
                        Genre genre = genreService.findByName(genreDtoName.getName());
                        if (genre == null){
                            throw new GenreNotFoundException(genreDtoName.getName());
                        }
                        return genre;
                    })
                    .collect(Collectors.toSet());

            game.setGenres(genres);
            game.setReleaseDate(LocalDateTime.now());
            gameRepository.save(game);
            communityService.saveCommunity(game);
        }else{
            throw new GameAlreadyExistsException(game.getName());
        }
    }

    @Override
    public List<GameDtoList> listGames(){
        List<Game> games = gameRepository.findAllOrderByFeedBackCountDescAndRatingDesc();
        return games.stream()
                .map(game -> modelMapper.map(game, GameDtoList.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Set<GameDtoName> recommendGamesByUserFeedback(int userId) {
        List<Game> gamesWithFeedbackByUser = gameRepository.findAllOrderByFeedBackUserRating(userId);
        if (gamesWithFeedbackByUser.isEmpty()) {
            throw new FeedbackNotFoundException("User with ID " + userId + " hasn't provided feedback.");
        }

        Set<GameDtoName> recommendGames = new HashSet<>();

        for (Game game : gamesWithFeedbackByUser) {

            float averageRating = game.getAverageFeedbackRating();
            if (averageRating < 7.0) {
                continue; // Пропускаем игры с рейтингом меньше 7.0
            }

            List<GenreDtoName> genreList = genreService.findAllByGameId(game.getId());
            List<String> genreNames = genreList.stream().map(GenreDtoName::getName).collect(Collectors.toList());

            List<Game> gamesByGenre = gameRepository.findAllByGenreNames(genreNames);
            gamesByGenre.removeAll(gamesWithFeedbackByUser);
            if (gamesByGenre.isEmpty()) {
                continue;
            }

            Game highestRatedGame = gamesByGenre.stream()
                    .max(Comparator.comparing(Game::getRating))
                    .orElse(null);

            if (highestRatedGame != null && highestRatedGame.getRating() >= 7.0) {
                recommendGames.add(modelMapper.map(highestRatedGame, GameDtoName.class));
            }
        }
        return recommendGames;
    }


    @Override
    public Game findByName(String name) {
        return gameRepository.findByName(name);
    }

    @Override
    public Game findById(int id) {
        return gameRepository.findById(id);
    }
}
