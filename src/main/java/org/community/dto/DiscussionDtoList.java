package org.community.dto;

public class DiscussionDtoList {
    private String creatorEmail;
    private String heading;
    private int commentCount;

    protected DiscussionDtoList() {
    }

    public DiscussionDtoList(String creatorEmail, String heading, int commentCount) {
        this.creatorEmail = creatorEmail;
        this.heading = heading;
        this.commentCount = commentCount;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
