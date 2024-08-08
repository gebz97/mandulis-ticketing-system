package org.mandulis.mts.data.repository;

import org.mandulis.mts.data.entity.Role;
import org.mandulis.mts.data.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {}
