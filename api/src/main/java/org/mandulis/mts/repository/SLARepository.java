package org.mandulis.mts.repository;

import org.mandulis.mts.entity.SLA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SLARepository extends JpaRepository<SLA, Long> {}
