package org.mandulis.mts.ticket;

import org.mandulis.mts.ticket.views.TicketSummaryProjectionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>,
        JpaSpecificationExecutor<Ticket> {

    Page<TicketSummaryProjectionView> findAllSummaries(Pageable pageable);
}
