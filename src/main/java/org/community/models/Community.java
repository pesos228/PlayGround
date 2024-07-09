package org.community.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Community {
    private int id;
    private Game game_name;

    public Community() {
    }

    public Community(Game game_name) {
        this.game_name = game_name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "game_name", nullable = false, unique = true)
    public Game getGame_name() {
        return game_name;
    }

    public void setGame_name(Game game_name) {
        this.game_name = game_name;
    }
}
