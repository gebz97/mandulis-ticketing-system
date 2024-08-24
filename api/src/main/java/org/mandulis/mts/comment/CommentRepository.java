package org.mandulis.mts.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByTicketId(Long ticketId);
    void deleteByUserId(Long userId);
    void deleteByUserIdAndTicketId(Long userId, Long ticketId);
    void deleteByTicketId(Long ticketId);
}