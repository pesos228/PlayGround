package org.community.service.impl;

import org.community.dto.FeedBackDto;
import org.community.dto.FeedBackDtoMy;
import org.community.dto.GameDtoRegister;
import org.community.entities.Feedback;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.exceptions.FeedbackAlreadyExistsException;
import org.community.exceptions.FeedbackNotFoundException;
import org.community.exceptions.GameNotFoundException;
import org.community.exceptions.UserNotFoundException;
import org.community.repository.FeedbackRepository;
import org.community.service.FeedbackService;
import org.community.service.GameService;
import org.community.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper, GameService gameService, UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void saveFeedback(FeedBackDto feedBackDto) {
        Game game = gameService.findByName(feedBackDto.getGameName());
        if (game == null){
            throw new GameNotFoundException(feedBackDto.getGameName());
        }

        User user = userService.findByEmail(feedBackDto.getUserEmail());
        if (user == null){
            throw new UserNotFoundException("User with mail "+ feedBackDto.getUserEmail() +" not found");
        }

        Feedback feedback = modelMapper.map(feedBackDto, Feedback.class);
        Feedback feedbackTest = feedbackRepository.findByGameIdAndUserId(user.getId(), game.getId());
        if (feedbackTest == null) {
            feedback.setGame(game);
            feedback.setUser(user);
            feedbackRepository.save(feedback);
            updateGameRating(feedback.getGame().getId());
            return;
        }

        throw new FeedbackAlreadyExistsException(feedback.getGame().getName());
    }

    @Override
    @Transactional
    public void updateGameRating(int gameId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByGameId(gameId);
        OptionalDouble averageRating = feedbacks.stream().mapToDouble(Feedback::getRating).average();
        float newRating = (float) (averageRating.isPresent() ? averageRating.getAsDouble() : 0.0);
        Game game = gameService.findById(gameId);
        if (game != null) {
            game.setRating(newRating);
            gameService.save(modelMapper.map(game, GameDtoRegister.class));
        } else {
            throw new GameNotFoundException(gameService.findById(gameId).getName());
        }
    }

    @Override
    @Transactional
    public void remove(int id){
        Feedback feedback = feedbackRepository.findById(id);
        if (feedback == null){
            throw new FeedbackNotFoundException("Feedback with ID "+ id + " not found");
        }
        feedbackRepository.deleteById(id);
        updateGameRating(feedback.getGame().getId());
    }

    @Override
    public List<FeedBackDtoMy> getUserFeedBacks(int id){
        User user = userService.findById(id);
        if (user == null){
            throw new UserNotFoundException("User with ID "+ id + " not found");
        }
        List<Feedback> list = feedbackRepository.findAllByUserId(user.getId());
        return list.stream()
                .map(feedback -> modelMapper.map(feedback, FeedBackDtoMy.class))
                .collect(Collectors.toList());
    }

    @Override
    public Feedback getUserFeedBackOnGame(int userId, int gameId){
        return feedbackRepository.findByGameIdAndUserId(userId, gameId);
    }

    @Override
    public List<FeedBackDto> getAllFeedBacksOnGame(String name){
        Game game = gameService.findByName(name);
        if (game == null){
            throw new GameNotFoundException(name);
        }
        List<Feedback> list = feedbackRepository.findAllByGameId(game.getId());
        return list.stream()
                .map(feedback -> modelMapper.map(feedback, FeedBackDto.class))
                .collect(Collectors.toList());
    }
}
