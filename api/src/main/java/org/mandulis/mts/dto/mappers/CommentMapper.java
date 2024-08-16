package org.mandulis.mts.dto.mappers;

import org.mandulis.mts.dto.request.CommentRequest;
import org.mandulis.mts.dto.response.CommentResponse;
import org.mandulis.mts.entity.Comment;
import org.mandulis.mts.entity.Ticket;
import org.mandulis.mts.entity.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentResponse toDto(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getTicket().getId(),
                comment.getUser().getId(),
                comment.getContent()
        );
    }

    public static Comment toEntity(CommentRequest commentRequest, User user, Ticket ticket) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(user);
        comment.setTicket(ticket);
        return comment;
    }
}

