package org.mandulis.mts.ticket;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.tag.Tag;

@Entity
@Table(name = "ticket_tags")
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
