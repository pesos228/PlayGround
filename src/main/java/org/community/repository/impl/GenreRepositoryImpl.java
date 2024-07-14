package org.community.repository.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.community.entities.Genre;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepositoryImpl extends AbstractBaseRepository<Genre, Integer> implements GenreRepository {

    public GenreRepositoryImpl() {
        super(Genre.class);
    }

    @Override
    public List<Genre> findAllByGameId(int id) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT gr FROM Genre gr JOIN gr.games g WHERE g.id = :gameId", Genre.class);
        query.setParameter("gameId", id);
        return query.getResultList();
    }

    @Override
    public List<Genre> findAllByGameName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT gr FROM Genre gr JOIN gr.games g WHERE LOWER(g.name) = LOWER(:gameName)", Genre.class);
        query.setParameter("gameName", name);
        return query.getResultList();
    }

    public Genre findByName(String name){
        try {
            TypedQuery<Genre> query = entityManager.createQuery("SELECT gr FROM Genre gr WHERE LOWER(gr.name) = LOWER(:name)", Genre.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
