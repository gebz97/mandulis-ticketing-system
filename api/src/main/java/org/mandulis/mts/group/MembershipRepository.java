package org.mandulis.mts.group;

import org.mandulis.mts.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByUserAndGroup(User user, Group group);
    Optional<Membership> findByUserAndGroup(User user, Group group);
}
