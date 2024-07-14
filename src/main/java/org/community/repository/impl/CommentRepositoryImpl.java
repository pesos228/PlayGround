package org.community.repository.impl;

import jakarta.persistence.TypedQuery;
import org.community.entities.Comment;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl extends AbstractBaseRepository<Comment, Integer> implements CommentRepository {
    public CommentRepositoryImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> findAllByDiscussionId(int discussionId) {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c JOIN c.discussion d WHERE d.id = :discussionId", Comment.class);
        query.setParameter("discussionId", discussionId);
        return query.getResultList();
    }
}
