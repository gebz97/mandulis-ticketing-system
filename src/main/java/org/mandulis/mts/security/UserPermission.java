package org.mandulis.mts.security;

import jakarta.persistence.*;
import lombok.*;
import org.mandulis.mts.user.User;

@Entity
@Table(name = "user_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
