package org.community.entities;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community")
public class Community extends BaseEntity {
    private Game game;
    private List<Discussion> discussions = new ArrayList<>();
    protected Community() {
    }

    public Community(Game game, List<Discussion> discussions) {
        this.game = game;
        this.discussions = discussions;
    }


    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }
}
