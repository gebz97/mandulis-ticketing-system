package org.mandulis.mts.service;

import org.mandulis.mts.data.entity.Attachment;
import org.mandulis.mts.data.repository.AttachmentRepository;
import org.mandulis.mts.data.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> findById(Long id) {
        return attachmentRepository.findById(id);
    }

    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public void deleteById(Long id) {
        attachmentRepository.deleteById(id);
    }

    public Attachment update(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }
}