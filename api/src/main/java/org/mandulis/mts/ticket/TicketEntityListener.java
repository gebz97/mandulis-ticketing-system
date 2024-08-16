package org.mandulis.mts.ticket;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class TicketEntityListener {

    @PrePersist
    public void prePersist(Ticket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Ticket ticket) {
        ticket.setUpdatedAt(LocalDateTime.now());
    }
}
