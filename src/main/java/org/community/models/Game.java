package org.community.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "game")
public class Game {
    private int id;
    private String name;
    private Set<Genre>genres = new HashSet<>();
    private String description;
    private LocalDateTime release_date;
    private float rating;
    private Community community;
    private List<Feedback> feedbacks = new ArrayList<>();

    public Game() {
    }

    public Game(String name, String description, LocalDateTime release_date) {
        this.name = name;
        this.description = description;
        this.release_date = release_date;
    }

    public Game(String name, String description, LocalDateTime release_date, float rating) {
        this.name = name;
        this.description = description;
        this.release_date = release_date;
        this.rating = rating;
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

    @ManyToMany
    @JoinTable(
            name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "release_date", nullable = false)
    public LocalDateTime getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDateTime release_date) {
        this.release_date = release_date;
    }

    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @OneToOne(mappedBy = "game_name", cascade = CascadeType.ALL)
    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @OneToMany(mappedBy = "game_id", cascade = CascadeType.ALL)
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
