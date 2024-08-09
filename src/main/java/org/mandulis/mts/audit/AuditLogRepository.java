package org.mandulis.mts.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
