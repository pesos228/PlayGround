package org.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionDtoList {
    private String creatorEmail;
    private String heading;
    private int commentCount;
}
