package org.community.service;

import org.community.dto.FeedBackDto;
import org.community.dto.FeedBackDtoMy;
import org.community.entities.Feedback;

import java.util.List;

public interface FeedbackService {

    void saveFeedback(FeedBackDto feedBackDto);

    void updateGameRating(int gameId);

    void remove(int id);

    List<FeedBackDtoMy> getUserFeedBacks(int id);

    Feedback getUserFeedBackOnGame(int userId, int gameId);

    List<FeedBackDto> getAllFeedBacksOnGame(String name);
}
