package org.mandulis.mts.category;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.ticket.Ticket;

import java.util.List;

@Entity
@Table(name = "categories")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;
}
