package org.mandulis.mts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
