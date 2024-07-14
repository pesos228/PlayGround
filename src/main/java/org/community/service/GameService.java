package org.community.service;

import org.community.entities.*;
import org.community.repository.impl.GameRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepositoryImpl gameRepository;
    private final GenreService genreService;
    private final FeedbackService feedbackService;
    private final CommunityService communityService;

    @Autowired
    public GameService(GameRepositoryImpl gameRepository, GenreService genreService, FeedbackService feedbackService, CommunityService communityService) {
        this.gameRepository = gameRepository;
        this.genreService = genreService;
        this.feedbackService = feedbackService;
        this.communityService = communityService;
    }

    @Transactional
    public void save(Game game){
        Game gameTest = gameRepository.findByName(game.getName());
        if (gameTest == null){
            gameRepository.save(game);
            communityService.saveCommunity(game);
        }else{
            throw new IllegalArgumentException("Game with name "+ game.getName() + " already exists");
        }
    }

    public List<Game> listGames(){
        return gameRepository.findAllOrderByFeedBackCountDescAndRatingDesc();
    }

    @Transactional
    public Set<Game> recommendGamesByUserFeedback(int userId){
        List<Game> gamesWithFeedBackByUser = gameRepository.findAllOrderByFeedBackUserRating(userId);
        if (gamesWithFeedBackByUser.isEmpty()){
            throw new IllegalArgumentException("User with ID "+ userId + " haven't feedbacks");
        }

        Set<Game> recommendGames = new HashSet<>();

        for (Game game: gamesWithFeedBackByUser){

            Feedback userRating = feedbackService.getUserFeedBackOnGame(userId, game.getId());
            if (userRating.getRating() < 7.0){
                continue;
            }

            List<Genre> genreList = genreService.findAllByGameId(game.getId());
            List<String> genreNames = genreList.stream().map(Genre::getName).collect(Collectors.toList());

            List<Game> gamesByGenre = gameRepository.findAllByGenreNames(genreNames);

            gamesByGenre.removeAll(gamesWithFeedBackByUser);
            if (gamesByGenre.isEmpty()){
                continue;
            }

            Game highestRatedGame = gamesByGenre.stream()
                    .max(Comparator.comparing(Game::getRating))
                    .orElse(null);

            if (highestRatedGame != null && highestRatedGame.getRating() >= 7.0) {
                recommendGames.add(highestRatedGame);
            }
        }
        return recommendGames;
    }

}
