package org.community.service;

import org.community.dto.CommunityDto;
import org.community.entities.Community;
import org.community.entities.Game;
import org.community.repository.CommunityRepository;
import org.community.repository.DiscussionRepository;
import org.community.repository.impl.CommunityRepositoryImpl;
import org.community.repository.impl.DiscussionRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityService extends AbstractService{
    private final CommunityRepository communityRepository;
    private final DiscussionRepository discussionRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository, ModelMapper modelMapper, DiscussionRepository discussionRepository) {
        super(modelMapper);
        this.communityRepository = communityRepository;
        this.discussionRepository = discussionRepository;
    }


    public Community findByGameId(int id){
        return communityRepository.findByGameId(id);
    }

    public Community findByGameName(String name){
        return communityRepository.findByGameName(name);
    }

    public List<CommunityDto> findAll(){
        return communityRepository.findAllOrderByDiscussionCountDescAndCommentCountDesc().stream()
                .map(community -> {
                    CommunityDto communityDto = convertToDto(community, CommunityDto.class);
                    communityDto.setDiscussionCount(discussionRepository.findAllByCommunityId(community.getId()).size());
                    return communityDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    void saveCommunity(Game game) {
        Community community = new Community(game, new ArrayList<>());
        communityRepository.save(community);
    }

}
