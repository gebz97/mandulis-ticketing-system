package org.mandulis.mts.comment;

import lombok.AllArgsConstructor;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
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
    public ResponseEntity<ApiResponse<CommentResponse>> createCommentByUsername(
            @RequestParam String username, @RequestBody CommentRequest commentRequest
    ) {
        return ResponseHandler.handleSuccess(
                commentService.createCommentByUsername(username, commentRequest),
                HttpStatus.CREATED,
                "Comment created successfully"
        );
    }

    @PostMapping("/by-userid")
    public ResponseEntity<ApiResponse<CommentResponse>> createCommentByUserId(
            @RequestParam Long userId, @RequestBody CommentRequest commentRequest
    ) {
        return ResponseHandler.handleSuccess(
                commentService.createCommentByUserId(userId, commentRequest),
                HttpStatus.CREATED,
                "Comment created successfully"
        );
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getAllCommentsByUser(@PathVariable Long userId) {
        return ResponseHandler.handleSuccess(
                commentService.getAllCommentsByUser(userId),
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }

    @GetMapping("/by-ticket/{ticketId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getAllCommentsByTicket(@PathVariable Long ticketId) {
        return ResponseHandler.handleSuccess(
                commentService.getAllCommentsByTicket(ticketId),
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId, @RequestBody CommentRequest commentRequest
    ) {
        return ResponseHandler.handleSuccess(
                commentService.updateComment(commentId, commentRequest),
                HttpStatus.OK,
                "Comment updated successfully"
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Comment deleted successfully");
    }

    @DeleteMapping("/by-user/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllCommentsByUser(@PathVariable Long userId) {
        commentService.deleteAllCommentsByUser(userId);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Comments deleted successfully");
    }

    @DeleteMapping("/by-user/{userId}/by-ticket/{ticketId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllCommentsByUserOnTicket(
            @PathVariable Long userId, @PathVariable Long ticketId
    ) {
        commentService.deleteAllCommentsByUserOnTicket(userId, ticketId);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Comments deleted successfully");
    }

    @DeleteMapping("/by-ticket/{ticketId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllCommentsByTicket(@PathVariable Long ticketId) {
        commentService.deleteAllCommentsByTicket(ticketId);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Comments deleted successfully");
    }
}
