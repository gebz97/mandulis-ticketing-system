package org.mandulis.mts.tag;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.ticket.Ticket;

import java.util.List;

@Entity
@Table(name = "tags")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ticket_tags",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Ticket> tickets;
}
