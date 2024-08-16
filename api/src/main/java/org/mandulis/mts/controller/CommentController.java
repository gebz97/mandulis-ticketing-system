package org.mandulis.mts.controller;

import lombok.AllArgsConstructor;
import org.mandulis.mts.dto.request.CommentRequest;
import org.mandulis.mts.dto.response.CommentResponse;
import org.mandulis.mts.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/by-username")
    public ResponseEntity<CommentResponse> createCommentByUsername(
            @RequestParam String username, @RequestBody CommentRequest commentRequest
    ) {
        CommentResponse commentResponse = commentService.createCommentByUsername(username, commentRequest);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @PostMapping("/by-userid")
    public ResponseEntity<CommentResponse> createCommentByUserId(
            @RequestParam Long userId, @RequestBody CommentRequest commentRequest
    ) {
        CommentResponse commentResponse = commentService.createCommentByUserId(userId, commentRequest);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByUser(@PathVariable Long userId) {
        List<CommentResponse> comments = commentService.getAllCommentsByUser(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/by-ticket/{ticketId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByTicket(@PathVariable Long ticketId) {
        List<CommentResponse> comments = commentService.getAllCommentsByTicket(ticketId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId, @RequestBody CommentRequest commentRequest
    ) {
        CommentResponse commentResponse = commentService.updateComment(commentId, commentRequest);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/by-user/{userId}")
    public ResponseEntity<Void> deleteAllCommentsByUser(@PathVariable Long userId) {
        commentService.deleteAllCommentsByUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/by-user/{userId}/by-ticket/{ticketId}")
    public ResponseEntity<Void> deleteAllCommentsByUserOnTicket(
            @PathVariable Long userId, @PathVariable Long ticketId
    ) {
        commentService.deleteAllCommentsByUserOnTicket(userId, ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/by-ticket/{ticketId}")
    public ResponseEntity<Void> deleteAllCommentsByTicket(@PathVariable Long ticketId) {
        commentService.deleteAllCommentsByTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

