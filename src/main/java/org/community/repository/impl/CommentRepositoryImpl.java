package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.community.entities.Comment;
import org.community.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> findAllByDiscussionIdOrderByTime(int discussionId) {
        return entityManager.createQuery("SELECT c FROM Comment c JOIN c.discussion d WHERE d.id = :discussionId ORDER BY d.createTime DESC", Comment.class)
            .setParameter("discussionId", discussionId)
            .getResultList();
    }

    @Override
    public void save(Comment comment) {
        if (entityManager.contains(comment)) {
            entityManager.merge(comment);
        } else {
            entityManager.persist(comment);
        }
    }
}
