package org.community.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {
    private User user;
    private Game game;
    private String text;
    private float rating;

    protected Feedback() {
    }

    public Feedback(User user, Game game, String text, float rating) {
        this.user = user;
        this.game = game;
        this.text = text;
        this.rating = rating;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "rating", nullable = false)
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
