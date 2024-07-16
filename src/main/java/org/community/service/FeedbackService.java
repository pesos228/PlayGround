package org.community.service;

import org.community.dto.FeedBackDto;
import org.community.dto.FeedBackDtoMy;
import org.community.entities.Feedback;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.exceptions.FeedbackAlreadyExistsException;
import org.community.exceptions.FeedbackNotFoundException;
import org.community.exceptions.GameNotFoundException;
import org.community.exceptions.UserNotFoundException;
import org.community.repository.impl.FeedbackRepositoryImpl;
import org.community.repository.impl.GameRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class FeedbackService extends AbstractService {

    private final FeedbackRepositoryImpl feedbackRepository;
    private final GameRepositoryImpl gameRepository;
    private final UserService userService;

    @Autowired
    public FeedbackService(FeedbackRepositoryImpl feedbackRepository, GameRepositoryImpl gameRepository, ModelMapper modelMapper, UserService userService) {
        super(modelMapper);
        this.feedbackRepository = feedbackRepository;
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveFeedback(FeedBackDto feedBackDto) {
        Game game = gameRepository.findByName(feedBackDto.getGameName());
        if (game == null){
            throw new GameNotFoundException(feedBackDto.getGameName());
        }

        User user = userService.findByEmail(feedBackDto.getUserEmail());
        if (user == null){
            throw new UserNotFoundException("User with mail "+ feedBackDto.getUserEmail() +" not found");
        }

        Feedback feedback = convertToEntity(feedBackDto, Feedback.class);
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

    @Transactional
    private void updateGameRating(int gameId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByGameId(gameId);
        OptionalDouble averageRating = feedbacks.stream().mapToDouble(Feedback::getRating).average();
        float newRating = (float) (averageRating.isPresent() ? averageRating.getAsDouble() : 0.0);
        Game game = gameRepository.findById(gameId);
        if (game != null) {
            game.setRating(newRating);
            gameRepository.save(game);
        } else {
            throw new GameNotFoundException(gameRepository.findById(gameId).getName());
        }
    }

    @Transactional
    public void remove(int id){
        Feedback feedback = feedbackRepository.findById(id);
        if (feedback == null){
            throw new FeedbackNotFoundException("Feedback with ID "+ id + " not found");
        }
        feedbackRepository.deleteById(id);
        updateGameRating(feedback.getGame().getId());
    }

    public List<FeedBackDtoMy> getUserFeedBacks(int id){
        User user = userService.findById(id);
        if (user == null){
            throw new UserNotFoundException("User with ID "+ id + " not found");
        }
        List<Feedback> list = feedbackRepository.findAllByUserId(user.getId());
        return list.stream()
                .map(feedback -> convertToDto(feedback, FeedBackDtoMy.class))
                .collect(Collectors.toList());
    }

    public Feedback getUserFeedBackOnGame(int userId, int gameId){
        return feedbackRepository.findByGameIdAndUserId(userId, gameId);
    }

    public List<FeedBackDto> getAllFeedBacksOnGame(String name){
        Game game = gameRepository.findByName(name);
        if (game == null){
            throw new GameNotFoundException(name);
        }
        List<Feedback> list = feedbackRepository.findAllByGameId(game.getId());
        return list.stream()
                .map(feedback -> convertToDto(feedback, FeedBackDto.class))
                .collect(Collectors.toList());
    }

}
