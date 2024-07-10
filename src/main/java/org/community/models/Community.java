package org.community.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "community")
public class Community {
    private int id;
    private Game game_name;
    private List<Discussion> discussions = new ArrayList<>();
    public Community() {
    }

    public Community(Game game_name, List<Discussion> discussions) {
        this.game_name = game_name;
        this.discussions = discussions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGame_name() {
        return game_name;
    }

    public void setGame_name(Game game_name) {
        this.game_name = game_name;
    }

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }
}
