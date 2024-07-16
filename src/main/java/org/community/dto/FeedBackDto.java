package org.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBackDto {
    private String userEmail;
    private String gameName;
    private String text;
    private float rating;
}
