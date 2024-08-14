package org.mandulis.mts.entity.stale;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.entity.Ticket;

import java.util.List;

@Entity
@Table(name = "slas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SLA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int responseTime; // in hours

    @Column(nullable = false)
    private int resolutionTime; // in hours

    @OneToMany(mappedBy = "sla", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;
}

