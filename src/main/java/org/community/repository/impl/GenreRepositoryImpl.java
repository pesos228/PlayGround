package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.community.entities.Genre;
import org.community.repository.GenreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> findAllByGameId(int id) {
        return entityManager.createQuery("SELECT gr FROM Genre gr JOIN gr.games g WHERE g.id = :gameId", Genre.class)
                .setParameter("gameId", id)
                .getResultList();
    }

    @Override
    public List<Genre> findAllByGameName(String name) {
        return entityManager.createQuery("SELECT gr FROM Genre gr JOIN gr.games g WHERE LOWER(g.name) = LOWER(:gameName)", Genre.class)
                .setParameter("gameName", name)
                .getResultList();
    }

    @Override
    public void save(Genre genre) {
        if (entityManager.contains(genre)) {
            entityManager.merge(genre);
        } else {
            entityManager.persist(genre);
        }
    }

    public Genre findByName(String name){
        try {
            return entityManager.createQuery("SELECT gr FROM Genre gr WHERE LOWER(gr.name) = LOWER(:name)", Genre.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
