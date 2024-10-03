package org.community.dto;

public class CommunityDto {
    private String gameName;
    private int discussionCount;

    protected CommunityDto() {
    }

    public CommunityDto(String gameName, int discussionCount) {
        this.gameName = gameName;
        this.discussionCount = discussionCount;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getDiscussionCount() {
        return discussionCount;
    }

    public void setDiscussionCount(int discussionCount) {
        this.discussionCount = discussionCount;
    }
}
