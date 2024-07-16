package org.community.service;

import org.community.dto.GameDtoList;
import org.community.dto.GameDtoName;
import org.community.dto.GameDtoRegister;
import org.community.dto.GenreDtoName;
import org.community.entities.*;
import org.community.exceptions.FeedbackNotFoundException;
import org.community.exceptions.GameAlreadyExistsException;
import org.community.exceptions.GenreNotFoundException;
import org.community.repository.impl.GameRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService extends AbstractService {

    private final GameRepositoryImpl gameRepository;
    private final GenreService genreService;
    private final FeedbackService feedbackService;
    private final CommunityService communityService;

    @Autowired
    public GameService(GameRepositoryImpl gameRepository, GenreService genreService, FeedbackService feedbackService, CommunityService communityService, ModelMapper modelMapper) {
        super(modelMapper);
        this.gameRepository = gameRepository;
        this.genreService = genreService;
        this.feedbackService = feedbackService;
        this.communityService = communityService;
    }

    @Transactional
    public void save(GameDtoRegister gameDtoRegister){
        Game game = convertToEntity(gameDtoRegister, Game.class);
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

    public List<GameDtoList> listGames(){
        List<Game> games = gameRepository.findAllOrderByFeedBackCountDescAndRatingDesc();
        return games.stream()
                .map(game -> convertToDto(game, GameDtoList.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Set<GameDtoName> recommendGamesByUserFeedback(int userId){
        List<Game> gamesWithFeedBackByUser = gameRepository.findAllOrderByFeedBackUserRating(userId);
        if (gamesWithFeedBackByUser.isEmpty()){
            throw new FeedbackNotFoundException("User with ID "+ userId + " haven't feedbacks");
        }

        Set<GameDtoName> recommendGames = new HashSet<>();

        for (Game game: gamesWithFeedBackByUser){

            Feedback userRating = feedbackService.getUserFeedBackOnGame(userId, game.getId());
            if (userRating.getRating() < 7.0){
                continue;
            }

            List<GenreDtoName> genreList = genreService.findAllByGameId(game.getId());
            List<String> genreNames = genreList.stream().map(GenreDtoName::getName).collect(Collectors.toList());

            List<Game> gamesByGenre = gameRepository.findAllByGenreNames(genreNames);

            gamesByGenre.removeAll(gamesWithFeedBackByUser);
            if (gamesByGenre.isEmpty()){
                continue;
            }

            Game highestRatedGame = gamesByGenre.stream()
                    .max(Comparator.comparing(Game::getRating))
                    .orElse(null);

            if (highestRatedGame != null && highestRatedGame.getRating() >= 7.0) {
                recommendGames.add(convertToDto(highestRatedGame, GameDtoName.class));
            }
        }
        return recommendGames;
    }

}
