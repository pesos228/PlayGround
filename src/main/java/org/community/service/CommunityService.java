package org.community.service;

import org.community.dto.CommunityDto;
import org.community.entities.Community;
import org.community.entities.Game;

import java.util.List;

public interface CommunityService {

    Community findByGameId(int id);

    Community findByGameName(String name);

    List<CommunityDto> findAll();

    void saveCommunity(Game game);

    void incrementDiscussionCount(int communityId);
}
