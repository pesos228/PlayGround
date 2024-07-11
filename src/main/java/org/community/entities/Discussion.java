package org.community.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discussion")
public class Discussion extends BaseEntity {
    private String heading;
    private String description;
    private LocalDateTime createTime;
    private User creatorId;
    private LocalDateTime closeTime;
    private List<Comment> comments = new ArrayList<>();
    private Community community;

    protected Discussion() {
    }

    public Discussion(String heading, String description, LocalDateTime createTime, User creatorId, LocalDateTime closeTime, List<Comment> comments, Community community) {
        this.heading = heading;
        this.description = description;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.closeTime = closeTime;
        this.comments = comments;
        this.community = community;
    }


    @Column(name = "heading", nullable = false)
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "create_time", nullable = false)
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
    }
    @Column(name = "close_time")
    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    @OneToMany(mappedBy = "discussionId", cascade = CascadeType.ALL)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @ManyToOne
    @JoinColumn(name = "community_id", referencedColumnName = "id", nullable = false)
    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
