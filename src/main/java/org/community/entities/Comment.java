package org.community.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    private String text;
    private User authorId;
    private Discussion discussionId;
    private LocalDateTime commentTime;

    protected Comment() {
    }

    public Comment(String text, User authorId, Discussion discussionId, LocalDateTime commentTime) {
        this.text = text;
        this.authorId = authorId;
        this.discussionId = discussionId;
        this.commentTime = commentTime;
    }


    @Column(name = "text", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    @ManyToOne
    @JoinColumn(name = "discussion_id", referencedColumnName = "id", nullable = false)
    public Discussion getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(Discussion discussionId) {
        this.discussionId = discussionId;
    }

    @Column(name = "comment_time", nullable = false)
    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }
}
