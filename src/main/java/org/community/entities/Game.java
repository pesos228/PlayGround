package org.community.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game extends BaseEntity {
    private String name;
    private Set<Genre>genres = new HashSet<>();
    private String description;
    private LocalDateTime releaseDate;
    private float rating;
    private Community community;
    private List<Feedback> feedbacks = new ArrayList<>();

    protected Game() {
    }

    public Game(String name, String description, LocalDateTime releaseDate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public Game(String name, String description, LocalDateTime releaseDate, float rating) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
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
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Transient
    public float getAverageFeedbackRating() {
        if (feedbacks.isEmpty()) {
            return -1;
        }
        return (float) feedbacks.stream()
                .mapToDouble(Feedback::getRating)
                .average()
                .orElse(0);
    }


}
