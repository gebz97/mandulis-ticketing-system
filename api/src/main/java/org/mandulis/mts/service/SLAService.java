package org.mandulis.mts.service;

import org.mandulis.mts.entity.stale.SLA;
import org.mandulis.mts.repository.SLARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SLAService {

    private final SLARepository slaRepository;

    @Autowired
    public SLAService(SLARepository slaRepository) {
        this.slaRepository = slaRepository;
    }

    public List<SLA> findAll() {
        return slaRepository.findAll();
    }

    public Optional<SLA> findById(Long id) {
        return slaRepository.findById(id);
    }

    public SLA save(SLA sla) {
        return slaRepository.save(sla);
    }

    public void deleteById(Long id) {
        slaRepository.deleteById(id);
    }

    public SLA update(SLA sla) {
        return slaRepository.save(sla);
    }
}
