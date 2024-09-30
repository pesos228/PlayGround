package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.community.entities.Community;
import org.community.repository.CommunityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommunityRepositoryImpl implements CommunityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Community findByGameId(int gameId) {
        try {
            return entityManager.createQuery("SELECT c FROM Community c WHERE c.game.id = :gameId", Community.class)
                    .setParameter("gameId", gameId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Community findByGameName(String name) {
        try {
           return entityManager.createQuery("SELECT c FROM Community c WHERE LOWER(c.game.name) = LOWER(:name)", Community.class)
                   .setParameter("name", name)
                   .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Community> findAllOrderByDiscussionCountDescAndCommentCountDesc() {
        return entityManager.createQuery(
                "SELECT c FROM Community c LEFT JOIN c.discussions d LEFT JOIN d.comments com GROUP BY c.id ORDER BY COUNT(d) DESC, COUNT(com) DESC",
                Community.class
        )
                .getResultList();
    }

    @Override
    public void save(Community community) {
        if (entityManager.contains(community)) {
            entityManager.merge(community);
        } else {
            entityManager.persist(community);
        }
    }

    @Override
    public void incrementDiscussionCount(int communityId) {
        entityManager.createQuery("UPDATE Community c SET c.discussionCount = c.discussionCount + 1 WHERE c.id = :communityId")
                .setParameter("communityId", communityId)
                .executeUpdate();
    }
}