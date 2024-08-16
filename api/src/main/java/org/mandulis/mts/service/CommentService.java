package org.mandulis.mts.service;

import lombok.AllArgsConstructor;
import org.mandulis.mts.dto.mappers.CommentMapper;
import org.mandulis.mts.dto.request.CommentRequest;
import org.mandulis.mts.dto.response.CommentResponse;
import org.mandulis.mts.entity.Comment;
import org.mandulis.mts.entity.Ticket;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.exception.CommentNotFoundException;
import org.mandulis.mts.exception.TicketNotFoundException;
import org.mandulis.mts.exception.UserNotFoundException;
import org.mandulis.mts.repository.CommentRepository;
import org.mandulis.mts.repository.TicketRepository;
import org.mandulis.mts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    public CommentResponse createCommentByUsername(String username, CommentRequest commentRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return createComment(user, commentRequest);
    }

    public CommentResponse createCommentByUserId(Long userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return createComment(user, commentRequest);
    }

    private CommentResponse createComment(User user, CommentRequest commentRequest) {
        Ticket ticket = ticketRepository.findById(commentRequest.getTicketId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        Comment comment = CommentMapper.toEntity(commentRequest, user, ticket);
        comment.setCreatedAt(LocalDateTime.now()); // Set createdAt here for new comments

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toDto(savedComment);
    }

    public List<CommentResponse> getAllCommentsByUser(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream().map(CommentMapper::toDto).toList();
    }

    public List<CommentResponse> getAllCommentsByTicket(Long ticketId) {
        List<Comment> comments = commentRepository.findByTicketId(ticketId);
        return comments.stream().map(CommentMapper::toDto).toList();
    }

    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        comment.setContent(commentRequest.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return CommentMapper.toDto(updatedComment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteAllCommentsByUser(Long userId) {
        commentRepository.deleteByUserId(userId);
    }

    public void deleteAllCommentsByUserOnTicket(Long userId, Long ticketId) {
        commentRepository.deleteByUserIdAndTicketId(userId, ticketId);
    }

    public void deleteAllCommentsByTicket(Long ticketId) {
        commentRepository.deleteByTicketId(ticketId);
    }
}
