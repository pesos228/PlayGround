package org.community.repository.impl;

import jakarta.persistence.TypedQuery;
import org.community.entities.Feedback;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.FeedbackRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepositoryImpl extends AbstractBaseRepository<Feedback, Integer> implements FeedbackRepository {

    public FeedbackRepositoryImpl() {
        super(Feedback.class);
    }

    @Override
    public List<Feedback> findAllByGameId(int gameId) {
        TypedQuery<Feedback> query = entityManager.createQuery("SELECT f FROM Feedback f JOIN f.game g WHERE g.id = :gameId", Feedback.class);
        query.setParameter("gameId", gameId);
        return query.getResultList();
    }

    @Override
    public List<Feedback> findAllByUserId(int userId) {
        TypedQuery<Feedback> query = entityManager.createQuery("SELECT f FROM Feedback f JOIN f.user u WHERE u.id = :userId", Feedback.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Feedback findByGameIdAndUserId(int userId, int gameId) {
        try {
            TypedQuery<Feedback> query = entityManager.createQuery("SELECT f FROM Feedback f JOIN f.user u JOIN f.game g WHERE u.id = :userId AND g.id = :gameId", Feedback.class);
            query.setParameter("userId", userId);
            query.setParameter("gameId", gameId);
            return query.getSingleResult();
        }catch (NullPointerException e){
            return null;
        }
    }
    public void deleteById(int id){
        Feedback feedback = entityManager.find(Feedback.class, id);
        if (feedback != null){
            entityManager.remove(feedback);
        }
    }
}
