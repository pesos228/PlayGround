package org.community.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionDto {
    private String heading;
    private String description;
    private String creatorEmail;
    private String communityGameName;
}
