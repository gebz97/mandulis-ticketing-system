package org.mandulis.mts.security;

import org.mandulis.mts.ticket.Ticket;
import org.mandulis.mts.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final TicketRepository ticketRepository;

    @Autowired
    public SecurityService(final TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public boolean isTicketOwner(Long ticketId, UserDetails userDetails) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        return ticket != null &&
                ticket.getRequester().getUsername().equals(userDetails.getUsername());
    }

    public boolean isAssignedToTicket(Long ticketId, UserDetails userDetails) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        return ticket != null && ticket.getAsignees().stream()
                .anyMatch(user -> user.getUsername().equals(userDetails.getUsername()));
    }

    public boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
