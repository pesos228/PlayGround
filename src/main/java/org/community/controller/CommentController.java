package org.community.controller;

import org.community.dto.CommentDtoAdd;
import org.community.dto.CommentDtoList;
import org.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void addComment(@RequestBody CommentDtoAdd commentDtoAdd){
        commentService.add(commentDtoAdd);
    }

    @GetMapping("/{discussionId}")
    public List<CommentDtoList> getCommentsList(@PathVariable int discussionId){
        return commentService.commentsOfDiscussion(discussionId);
    }

}
