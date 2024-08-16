package org.mandulis.mts.repository;

import org.mandulis.mts.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByTicketId(Long ticketId);
    void deleteByUserId(Long userId);
    void deleteByUserIdAndTicketId(Long userId, Long ticketId);
    void deleteByTicketId(Long ticketId);
}