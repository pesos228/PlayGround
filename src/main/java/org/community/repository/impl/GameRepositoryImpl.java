package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.repository.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GameRepositoryImpl implements GameRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Game> findAllByGenreIds(List<Integer> ids) {
       return entityManager.createQuery("SELECT DISTINCT g FROM Game g JOIN g.genres gen WHERE gen.id IN :ids", Game.class)
            .setParameter("ids", ids)
            .getResultList();
    }

    @Override
    public List<Game> findAllByGenreNames(List<String> names) {
        List<String> lowerCaseNames = names.stream().map(String::toLowerCase).collect(Collectors.toList());
        return entityManager.createQuery("SELECT DISTINCT g FROM Game g JOIN g.genres gen WHERE LOWER(gen.name) IN :names", Game.class)
                .setParameter("names", lowerCaseNames)
                .getResultList();
    }

    @Override
    public Game findByName(String name) {
        try {
           return entityManager.createQuery("SELECT g FROM Game g WHERE LOWER(g.name) = LOWER(:name)", Game.class)
                .setParameter("name", name)
                .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Game> findAllOrderByFeedBackCountDescAndRatingDesc() {
        return entityManager.createQuery("SELECT g FROM Game g LEFT JOIN g.feedbacks f GROUP BY g.id ORDER BY COUNT(f) DESC, g.rating DESC", Game.class)
            .getResultList();
    }

    @Override
    public List<Game> findAllOrderByFeedBackUserRating(int id) {
        return entityManager.createQuery("SELECT g FROM Game g LEFT JOIN g.feedbacks f WHERE f.user.id = :userId ORDER BY g.rating DESC", Game.class)
            .setParameter("userId", id)
            .getResultList();
    }

    @Override
    public void save(Game game) {
        if (entityManager.contains(game)) {
            entityManager.merge(game);
        } else {
            entityManager.persist(game);
        }
    }

    @Override
    public Game findById(int id) {
        return entityManager.find(Game.class, id);
    }
}
