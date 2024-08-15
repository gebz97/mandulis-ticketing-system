package org.mandulis.mts.repository;

import org.mandulis.mts.entity.Group;
import org.mandulis.mts.entity.Membership;
import org.mandulis.mts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByUserAndGroup(User user, Group group);
    Optional<Membership> findByUserAndGroup(User user, Group group);
}
