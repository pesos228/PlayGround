package org.community.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Discussion {
    private int id;
    private String heading;
    private String description;
    private LocalDateTime create_time;
    private User creator_id;
    private LocalDateTime close_time;
    private List<Comment> comments = new ArrayList<>();

    public Discussion() {
    }

    public Discussion(int id, String heading, String description, LocalDateTime create_time, User creator_id, LocalDateTime close_time, List<Comment> comments) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.create_time = create_time;
        this.creator_id = creator_id;
        this.close_time = close_time;
        this.comments = comments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "heading", nullable = false)
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "create_time", nullable = false)
    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }
    @Column(name = "creator_id", nullable = false)
    public User getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(User creator_id) {
        this.creator_id = creator_id;
    }
    @Column(name = "close_time")
    public LocalDateTime getClose_time() {
        return close_time;
    }

    public void setClose_time(LocalDateTime close_time) {
        this.close_time = close_time;
    }

    @Column(name = "comments")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
