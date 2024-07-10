package org.community.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "feedback")
public class Feedback {
    private int id;
    private User user_id;
    private Game game_id;
    private String text;

    public Feedback() {
    }

    public Feedback(User user_id, Game game_id, String text) {
        this.user_id = user_id;
        this.game_id = game_id;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGame_id() {
        return game_id;
    }

    public void setGame_id(Game game_id) {
        this.game_id = game_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
