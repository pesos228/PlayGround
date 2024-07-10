package org.community.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "genre")
public class Genre {
    private int id;
    private String name;
    private Set<Game> games = new HashSet<>();

    public Genre() {
    }

    public Genre(String name, Set<Game> games) {
        this.name = name;
        this.games = games;
    }


    public Genre(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
