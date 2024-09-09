package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.community.entities.Feedback;
import org.community.entities.User;
import org.community.repository.FeedbackRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Feedback> findAllByGameId(int gameId) {
        return entityManager.createQuery("SELECT f FROM Feedback f JOIN f.game g WHERE g.id = :gameId", Feedback.class)
                .setParameter("gameId", gameId)
                .getResultList();
    }

    @Override
    public List<Feedback> findAllByUserId(int userId) {
        return entityManager.createQuery("SELECT f FROM Feedback f JOIN f.user u WHERE u.id = :userId", Feedback.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    @Override
    public Feedback findByGameIdAndUserId(int userId, int gameId) {
        try {
           return entityManager.createQuery("SELECT f FROM Feedback f JOIN f.user u JOIN f.game g WHERE u.id = :userId AND g.id = :gameId", Feedback.class)
                .setParameter("userId", userId)
                .setParameter("gameId", gameId)
                .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void save(Feedback feedback) {
        if (entityManager.contains(feedback)) {
            entityManager.merge(feedback);
        } else {
            entityManager.persist(feedback);
        }
    }

    @Override
    public Feedback findById(int id) {
        return entityManager.find(Feedback.class, id);
    }

    public void deleteById(int id){
        Feedback feedback = entityManager.find(Feedback.class, id);
        if (feedback != null){
            entityManager.remove(feedback);
        }
    }
}
