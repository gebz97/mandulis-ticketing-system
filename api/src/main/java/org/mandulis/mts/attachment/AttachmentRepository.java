package org.mandulis.mts.attachment;

import org.mandulis.mts.attachment.views.MinimalAttachmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("SELECT a.id AS id, a.fileName AS fileName, a.description AS description, a.ticket.id AS ticketId FROM " +
            "Attachment a WHERE a.ticket.id = :ticketId")
    List<MinimalAttachmentView> findAllByTicketId(@Param("ticketId") Long ticketId);
}