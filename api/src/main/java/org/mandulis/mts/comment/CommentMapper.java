package org.mandulis.mts.comment;

import org.mandulis.mts.ticket.Ticket;
import org.mandulis.mts.user.User;

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

