package org.mandulis.mts.sla;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SLARepository extends JpaRepository<SLA, Long> {}
