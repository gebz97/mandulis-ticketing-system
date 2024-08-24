package org.mandulis.mts.attachment;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.ticket.Ticket;

@Entity
@Table(name = "attachments")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}