package org.mandulis.mts.entity.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.mandulis.mts.entity.Ticket;

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
