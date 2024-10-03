package org.community.dto;

public class DiscussionDto {
    private String heading;
    private String description;
    private String creatorEmail;
    private String communityGameName;

    protected DiscussionDto() {
    }

    public DiscussionDto(String heading, String description, String creatorEmail, String communityGameName) {
        this.heading = heading;
        this.description = description;
        this.creatorEmail = creatorEmail;
        this.communityGameName = communityGameName;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCommunityGameName() {
        return communityGameName;
    }

    public void setCommunityGameName(String communityGameName) {
        this.communityGameName = communityGameName;
    }
}
