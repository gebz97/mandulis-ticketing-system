package org.mandulis.mts.repository;

import org.mandulis.mts.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>,
        JpaSpecificationExecutor<Ticket> {
}
