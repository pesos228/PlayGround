package org.community.repository.impl;

import jakarta.persistence.TypedQuery;
import org.community.entities.Discussion;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.DiscussionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiscussionRepositoryImpl extends AbstractBaseRepository<Discussion, Integer> implements DiscussionRepository {

    public DiscussionRepositoryImpl() {
        super(Discussion.class);
    }

    @Override
    public boolean existsByCreatorIdAndDiscussionId(int userId, int discussionId) {
        TypedQuery<Boolean> query = entityManager.createQuery("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM Discussion d WHERE d.creator.id = :userId AND d.id = :discussionId", Boolean.class);
        query.setParameter("userId", userId);
        query.setParameter("discussionId", discussionId);
        return query.getSingleResult();
    }

    @Override
    public boolean isDiscussionClosed(int discussionId) {
        TypedQuery<Boolean> query = entityManager.createQuery("SELECT CASE WHEN d.closeTime IS NULL THEN FALSE ELSE TRUE END FROM Discussion d WHERE d.id = :discussionId", Boolean.class);
        query.setParameter("discussionId", discussionId);
        return query.getSingleResult();
    }

    @Override
    public List<Discussion> findByHeading(String name) {
        TypedQuery<Discussion> query = entityManager.createQuery("SELECT d FROM Discussion d WHERE LOWER(d.heading) = LOWER(:name)", Discussion.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Discussion> findByCreatorId(int id) {
        TypedQuery<Discussion> query = entityManager.createQuery("SELECT d FROM Discussion d JOIN d.creator c WHERE c.id = :creatorId", Discussion.class);
        query.setParameter("creatorId", id);
        return query.getResultList();
    }

    @Override
    public List<Discussion> findAllByCommunityId(int id) {
        TypedQuery<Discussion> query = entityManager.createQuery("SELECT d FROM Discussion d WHERE d.community.id = :communityId",Discussion.class);
        query.setParameter("communityId", id);
        return query.getResultList();
    }

    @Override
    public List<Discussion> findAllByCommunityIdAndOrderByTime(int id) {
        TypedQuery<Discussion> query = entityManager.createQuery("SELECT d FROM Discussion d WHERE d.community.id = :communityId ORDER BY d.createTime DESC", Discussion.class);
        query.setParameter("communityId", id);
        return query.getResultList();
    }
}
