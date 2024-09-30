package org.community.service;

import org.community.dto.DiscussionDto;
import org.community.dto.DiscussionDtoClose;
import org.community.dto.DiscussionDtoList;
import org.community.dto.DiscussionDtoMy;
import org.community.entities.Discussion;

import java.util.List;

public interface DiscussionService {

    void create(DiscussionDto discussionDto);

    void close(DiscussionDtoClose discussionDtoClose);

    List<Discussion> searchDiscussions(String name);

    boolean isDiscussionClosed(int id);

    List<DiscussionDtoMy> findUserDiscussions(int id);

    Discussion findById(int id);

    List<DiscussionDtoList> listOfDiscussionsByCommunity(String name);

    List<Discussion> findAllByCommunityId(int id);

    int getDiscussionCountForCommunity(int communityId);

    void incrementCommentCount(int discussionId);
}
