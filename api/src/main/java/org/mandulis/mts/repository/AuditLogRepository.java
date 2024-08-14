package org.mandulis.mts.repository;

import org.mandulis.mts.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
