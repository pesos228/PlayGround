package org.community.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre extends BaseEntity {
    private String name;
    private Set<Game> games = new HashSet<>();

    protected Genre() {
    }

    public Genre(String name, Set<Game> games) {
        this.name = name;
        this.games = games;
    }

    public Genre(String name) {
        this.name = name;
    }


    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "genres")
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
