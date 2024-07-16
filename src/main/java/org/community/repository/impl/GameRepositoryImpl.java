package org.community.repository.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.community.entities.Game;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GameRepositoryImpl extends AbstractBaseRepository<Game, Integer> implements GameRepository {
    public GameRepositoryImpl(){
        super(Game.class);
    }

    @Override
    public List<Game> findAllByGenreIds(List<Integer> ids) {
        TypedQuery<Game> query = entityManager.createQuery("SELECT DISTINCT g FROM Game g JOIN g.genres gen WHERE gen.id IN :ids", Game.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public List<Game> findAllByGenreNames(List<String> names) {
        List<String> lowerCaseNames = names.stream().map(String::toLowerCase).collect(Collectors.toList());
        TypedQuery<Game> query = entityManager.createQuery("SELECT DISTINCT g FROM Game g JOIN g.genres gen WHERE LOWER(gen.name) IN :names", Game.class);
        query.setParameter("names", lowerCaseNames);
        return query.getResultList();
    }

    @Override
    public Game findByName(String name) {
        try {
            TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g WHERE LOWER(g.name) = LOWER(:name)", Game.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Game> findAllOrderByFeedBackCountDescAndRatingDesc() {
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g LEFT JOIN g.feedbacks f GROUP BY g.id ORDER BY COUNT(f) DESC, g.rating DESC", Game.class);
        return query.getResultList();
    }

    @Override
    public List<Game> findAllOrderByFeedBackUserRating(int id) {
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g LEFT JOIN g.feedbacks f WHERE f.user.id = :userId ORDER BY g.rating DESC", Game.class);
        query.setParameter("userId", id);
        return query.getResultList();
    }
}
