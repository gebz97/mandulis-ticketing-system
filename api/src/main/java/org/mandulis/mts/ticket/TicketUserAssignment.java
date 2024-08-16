package org.mandulis.mts.ticket;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.user.User;

@Entity
@Table(name = "ticket_assignments")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketUserAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
