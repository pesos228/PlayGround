package org.community.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {
    private User userId;
    private Game gameId;
    private String text;

    protected Feedback() {
    }

    public Feedback(User userId, Game gameId, String text) {
        this.userId = userId;
        this.gameId = gameId;
        this.text = text;
    }


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
