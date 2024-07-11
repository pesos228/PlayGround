package org.community.entities;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community")
public class Community extends BaseEntity {
    private Game gameName;
    private List<Discussion> discussions = new ArrayList<>();
    protected Community() {
    }

    public Community(Game gameName, List<Discussion> discussions) {
        this.gameName = gameName;
        this.discussions = discussions;
    }


    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public Game getGameName() {
        return gameName;
    }

    public void setGameName(Game gameName) {
        this.gameName = gameName;
    }

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }
}
