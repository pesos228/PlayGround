package org.community.repository;

import org.community.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c JOIN c.discussion d WHERE d.id = :discussionId")
    List<Comment> findAllByDiscussionId(@Param(value = "discussionId") int discussionId);
    @Query("SELECT c FROM Comment c JOIN c.author a WHERE a.id = :authorId")
    List<Comment> findByAllByAuthorId(@Param(value = "authorId") int id);
    @Query("SELECT c FROM Comment c JOIN c.author a JOIN c.discussion d WHERE d.id = :discussionId AND a.id = :authorId")
    List<Comment> findByAuthorIdAndDiscussionId(@Param(value = "authorId")int id, @Param(value = "discussionId") int discussionId);
    @Query("SELECT c FROM Comment c WHERE LOWER(c.text) = LOWER(:text)")
    List<Comment> findByText(@Param(value = "text") String text);
    @Query("SELECT c FROM Comment c JOIN c.author a WHERE LOWER(c.text) = LOWER(:text) AND a.id = :authorId")
    List<Comment> findByTextAndAuthorId(@Param(value = "text")String text, @Param(value = "authorId")int id);
    @Query("SELECT c FROM Comment c JOIN c.author a JOIN c.discussion d WHERE d.id = :discussionId AND a.id = :authorId AND LOWER(c.text) = LOWER(:text)")
    List<Comment> findByTextAndAuthorIdAndDiscussionId(@Param(value = "text")String text, @Param(value = "authorId")int authorId, @Param(value = "discussionId")int discussionId);
}
