package org.community.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    private String text;
    private User author;
    private Discussion discussion;
    private LocalDateTime commentTime;

    protected Comment() {
    }

    public Comment(String text, User author, Discussion discussion, LocalDateTime commentTime) {
        this.text = text;
        this.author = author;
        this.discussion = discussion;
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
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "discussion_id", referencedColumnName = "id", nullable = false)
    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    @Column(name = "comment_time", nullable = false)
    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }
}
