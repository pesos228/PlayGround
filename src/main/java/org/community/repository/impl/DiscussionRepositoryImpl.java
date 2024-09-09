package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.community.entities.Discussion;
import org.community.repository.DiscussionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiscussionRepositoryImpl implements DiscussionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsByCreatorIdAndDiscussionId(int userId, int discussionId) {
       return entityManager.createQuery("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM Discussion d WHERE d.creator.id = :userId AND d.id = :discussionId", Boolean.class)
                .setParameter("userId", userId)
                .setParameter("discussionId", discussionId)
                .getSingleResult();
    }

    @Override
    public boolean isDiscussionClosed(int discussionId) {
        return entityManager.createQuery("SELECT CASE WHEN d.closeTime IS NULL THEN FALSE ELSE TRUE END FROM Discussion d WHERE d.id = :discussionId", Boolean.class)
                .setParameter("discussionId", discussionId)
                .getSingleResult();
    }

    @Override
    public List<Discussion> findByHeading(String name) {
        return entityManager.createQuery("SELECT d FROM Discussion d WHERE LOWER(d.heading) = LOWER(:name)", Discussion.class)
            .setParameter("name", name)
            .getResultList();
    }

    @Override
    public List<Discussion> findByCreatorId(int id) {
        return entityManager.createQuery("SELECT d FROM Discussion d JOIN d.creator c WHERE c.id = :creatorId", Discussion.class)
            .setParameter("creatorId", id)
            .getResultList();
    }

    @Override
    public List<Discussion> findAllByCommunityId(int id) {
        return entityManager.createQuery("SELECT d FROM Discussion d WHERE d.community.id = :communityId",Discussion.class)
                .setParameter("communityId", id)
                .getResultList();
    }

    @Override
    public List<Discussion> findAllByCommunityIdAndOrderByTime(int id) {
       return entityManager.createQuery("SELECT d FROM Discussion d WHERE d.community.id = :communityId ORDER BY d.createTime DESC", Discussion.class)
               .setParameter("communityId", id)
               .getResultList();
    }

    @Override
    public void save(Discussion discussion) {
        if (entityManager.contains(discussion)) {
            entityManager.merge(discussion);
        } else {
            entityManager.persist(discussion);
        }
    }

    @Override
    public Discussion findById(int id) {
        return entityManager.find(Discussion.class, id);
    }
}
