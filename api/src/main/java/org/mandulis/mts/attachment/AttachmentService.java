package org.mandulis.mts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.entity.Attachment;
import org.mandulis.mts.entity.Ticket;
import org.mandulis.mts.exception.TicketNotFoundException;
import org.mandulis.mts.repository.AttachmentRepository;
import org.mandulis.mts.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final StorageService storageService;
    private final TicketRepository ticketRepository;

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> findById(Long id) {
        return attachmentRepository.findById(id);
    }

    public Attachment save(AttachmentDto dto) {
        Ticket ticket = ticketRepository.findById(dto.ticketId).orElseThrow(
                () -> new TicketNotFoundException(String.format("Ticket[id=%d] not found.", dto.ticketId))
        );
        Path storedFilePath = storageService.store(dto.fileName, dto.inputStream);
        Attachment attachment = createAttachmentEntity(dto, ticket, storedFilePath);
        attachmentRepository.save(attachment);
        log.info("Saved new attachment[id={}].", attachment.getId());
        return attachment;
    }

    public void deleteById(Long id) {
        attachmentRepository.deleteById(id);
    }

    public Attachment update(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    private Attachment createAttachmentEntity(AttachmentDto dto, Ticket ticket, Path storedFilePath) {
        Attachment attachment = new Attachment();
        attachment.setFileName(dto.fileName);
        attachment.setFilePath(storedFilePath.toString());
        attachment.setTicket(ticket);
        return attachment;
    }
}