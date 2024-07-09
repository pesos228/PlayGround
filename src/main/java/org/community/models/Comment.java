package org.community.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Comment {
    private int id;
    private String text;
    private User author_id;
    private Discussion discussion_id;
    private LocalDateTime comment_time;

    public Comment() {
    }

    public Comment(int id, String text, User author_id, Discussion discussion_id, LocalDateTime comment_time) {
        this.id = id;
        this.text = text;
        this.author_id = author_id;
        this.discussion_id = discussion_id;
        this.comment_time = comment_time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "text", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "author_id", nullable = false)
    public User getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(User author_id) {
        this.author_id = author_id;
    }

    @Column(name = "discussion_id", nullable = false)
    public Discussion getDiscussion_id() {
        return discussion_id;
    }

    public void setDiscussion_id(Discussion discussion_id) {
        this.discussion_id = discussion_id;
    }

    @Column(name = "comment_time", nullable = false)
    public LocalDateTime getComment_time() {
        return comment_time;
    }

    public void setComment_time(LocalDateTime comment_time) {
        this.comment_time = comment_time;
    }
}
