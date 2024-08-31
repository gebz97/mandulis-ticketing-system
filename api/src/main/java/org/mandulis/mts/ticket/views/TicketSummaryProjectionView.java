package org.mandulis.mts.ticket.views;

import org.mandulis.mts.ticket.Ticket;

import java.time.LocalDateTime;

public interface TicketSummaryProjectionView {
    Long getId();
    String getTitle();
    String getCategory();
    Ticket.Priority getPriority();
    Ticket.Status getStatus();
    LocalDateTime getCreatedDate();

    LocalDateTime getUpdatedDate();
}
