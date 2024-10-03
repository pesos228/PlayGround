package org.community.dto;

public class DiscussionDtoMy {
    private int id;
    private String heading;
    private int commentCount;

    protected DiscussionDtoMy() {
    }

    public DiscussionDtoMy(int id, String heading, int commentCount) {
        this.id = id;
        this.heading = heading;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
