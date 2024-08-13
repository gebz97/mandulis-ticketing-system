package org.mandulis.mts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_assignments")
@Data
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
