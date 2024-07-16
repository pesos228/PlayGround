package org.community.controller;

import org.community.dto.DiscussionDto;
import org.community.dto.DiscussionDtoClose;
import org.community.dto.DiscussionDtoList;
import org.community.dto.DiscussionDtoMy;
import org.community.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {

    private final DiscussionService discussionService;

    @Autowired
    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping
    public void create(@RequestBody DiscussionDto discussionDto){
        discussionService.create(discussionDto);
    }

    @GetMapping("/{gameName}")
    public List<DiscussionDtoList> getDiscussionListByGame(@PathVariable String gameName){
        return discussionService.listOfDiscussionsByCommunity(gameName);
    }
    @GetMapping("/my/{userId}")
    public List<DiscussionDtoMy> getAllDiscussionsByUser(@PathVariable int userId){
        return discussionService.findUserDiscussions(userId);
    }

    @PostMapping("/close")
    public void closeDiscussion(@RequestBody DiscussionDtoClose discussionDtoClose){
        discussionService.close(discussionDtoClose);
    }
}
