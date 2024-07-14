package org.community.service;

import org.community.entities.Feedback;
import org.community.entities.Game;
import org.community.repository.impl.FeedbackRepositoryImpl;
import org.community.repository.impl.GameRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class FeedbackService {

    private final FeedbackRepositoryImpl feedbackRepository;
    private final GameRepositoryImpl gameRepository;

    @Autowired
    public FeedbackService(FeedbackRepositoryImpl feedbackRepository, GameRepositoryImpl gameRepository) {
        this.feedbackRepository = feedbackRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void saveFeedback(Feedback feedback) {
        Feedback feedbackTest = feedbackRepository.findByGameIdAndUserId(feedback.getUser().getId(), feedback.getGame().getId());
        if (feedbackTest == null) {
            feedbackRepository.save(feedback);
            updateGameRating(feedback.getGame().getId());
            return;
        }
        throw new IllegalArgumentException("A feedback of " + feedback.getGame().getName() + " already exists");
    }

    @Transactional
    private void updateGameRating(int gameId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByGameId(gameId);
        OptionalDouble averageRating = feedbacks.stream().mapToDouble(Feedback::getRating).average();
        averageRating.ifPresent(avg -> {
            Game game = gameRepository.findById(gameId);
            if (game != null) {
                game.setRating((float) avg);
                gameRepository.save(game);
            } else {
                throw new IllegalArgumentException("Game with ID " + gameId + " doesn't exist");
            }
        });
    }

    public void remove(int id){
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> getUserFeedBacks(int id){
        return feedbackRepository.findAllByUserId(id);
    }

    public Feedback getUserFeedBackOnGame(int userId, int gameId){
        return feedbackRepository.findByGameIdAndUserId(userId, gameId);
    }

    public List<Feedback> getAllFeedBacksOnGame(int gameId){
        return feedbackRepository.findAllByGameId(gameId);
    }

}
