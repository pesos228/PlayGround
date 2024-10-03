package org.community.dto;

public class DiscussionDtoClose {
    private int id;
    private int creatorId;

    protected DiscussionDtoClose() {
    }

    public DiscussionDtoClose(int id, int creatorId) {
        this.id = id;
        this.creatorId = creatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
