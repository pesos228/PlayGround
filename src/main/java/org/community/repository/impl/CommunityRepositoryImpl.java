package org.community.repository.impl;

import jakarta.persistence.TypedQuery;
import org.community.entities.Community;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.CommunityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommunityRepositoryImpl extends AbstractBaseRepository<Community, Integer> implements CommunityRepository {

    public CommunityRepositoryImpl() {
        super(Community.class);
    }

    @Override
    public Community findByGameId(int gameId) {
        try {
            TypedQuery<Community> query = entityManager.createQuery("SELECT c FROM Community c WHERE c.game.id = :gameId", Community.class);
            query.setParameter("gameId", gameId);
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    public Community findByGameName(String name) {
        try {
            TypedQuery<Community> query = entityManager.createQuery("SELECT c FROM Community c WHERE LOWER(c.game.name) = LOWER(:name)", Community.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Community> findAllOrderByDiscussionCountDescAndCommentCountDesc() {
        TypedQuery<Community> query = entityManager.createQuery(
                "SELECT c FROM Community c LEFT JOIN c.discussions d LEFT JOIN d.comments com GROUP BY c.id ORDER BY COUNT(d) DESC, COUNT(com) DESC",
                Community.class
        );
        return query.getResultList();
    }
}