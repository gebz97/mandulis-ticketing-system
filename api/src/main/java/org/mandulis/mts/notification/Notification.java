package org.mandulis.mts.notification;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean read;
}

