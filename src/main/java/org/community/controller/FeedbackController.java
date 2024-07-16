package org.community.controller;

import org.community.dto.FeedBackDto;
import org.community.dto.FeedBackDtoMy;
import org.community.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public void createFeedBack(@RequestBody FeedBackDto feedBackDto){
        feedbackService.saveFeedback(feedBackDto);
    }

    @GetMapping("/{userId}")
    public List<FeedBackDtoMy> myFeedBacks(@PathVariable int userId){
        return feedbackService.getUserFeedBacks(userId);
    }

    @GetMapping("/game/{gameName}")
    public List<FeedBackDto> feedBackList(@PathVariable String gameName){
        return feedbackService.getAllFeedBacksOnGame(gameName);
    }

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedback(@PathVariable int feedbackId){
        feedbackService.remove(feedbackId);
    }
}
