package org.community.service.impl;

import org.community.dto.CommunityDto;
import org.community.entities.Community;
import org.community.entities.Game;
import org.community.repository.CommunityRepository;
import org.community.service.CommunityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommunityServiceImpl(CommunityRepository communityRepository, ModelMapper modelMapper) {
        this.communityRepository = communityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Community findByGameId(int id){
        return communityRepository.findByGameId(id);
    }

    @Override
    public Community findByGameName(String name){
        return communityRepository.findByGameName(name);
    }

    @Override
    public List<CommunityDto> findAll() {
        return communityRepository.findAllOrderByDiscussionCountDescAndCommentCountDesc().stream()
                .map(community -> {
                    CommunityDto communityDto = modelMapper.map(community, CommunityDto.class);
                    communityDto.setDiscussionCount(community.getDiscussions().size());
                    return communityDto;
                })
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void saveCommunity(Game game) {
        Community community = new Community(game, new ArrayList<>());
        communityRepository.save(community);
    }

    @Override
    @Transactional
    public void incrementDiscussionCount(int communityId) {
        communityRepository.incrementDiscussionCount(communityId);
    }


}
