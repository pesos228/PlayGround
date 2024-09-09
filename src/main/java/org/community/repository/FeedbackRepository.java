package org.community.repository;

import org.community.entities.Feedback;

import java.util.List;

public interface FeedbackRepository {
    List<Feedback> findAllByGameId(int id);
    List<Feedback> findAllByUserId(int id);
    Feedback findByGameIdAndUserId(int userId, int gameId);
    void save(Feedback feedback);
    Feedback findById(int id);
    void deleteById(int id);
}
