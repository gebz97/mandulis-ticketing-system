package org.mandulis.mts.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mandulis.mts.attachment.views.MinimalAttachmentView;
import org.mandulis.mts.exception.AttachmentNotFoundException;
import org.mandulis.mts.exception.TicketNotFoundException;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.ticket.Ticket;
import org.mandulis.mts.ticket.TicketRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@ConditionalOnBean(name = "storageService")
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final StorageService storageService;
    private final TicketRepository ticketRepository;

    public AttachmentResponse saveAttachment(AttachmentRequest request, MultipartFile file) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(request.getTicketId());
        if (ticketOptional.isEmpty()) {
            throw new TicketNotFoundException("Ticket not found");
        }

        Ticket ticket = ticketOptional.get();
        String preSignedUri = storageService.store(file);

        Attachment attachment = new Attachment();
        attachment.setFileName(request.getName());
        attachment.setDescription(request.getDescription());
        attachment.setTicket(ticket);

        attachmentRepository.save(attachment);

        return AttachmentMapper.toResponse(attachment, preSignedUri);
    }

    public List<AttachmentResponse> getAllAttachmentsByTicketId(Long ticketId) {
        List<MinimalAttachmentView> attachmentViews = attachmentRepository.findAllByTicketId(ticketId);
        return attachmentViews.stream()
                .map(attachment -> {
                    String preSignedUri = storageService.retrieve(attachment.getFileName());
                    return AttachmentMapper.minimalViewToResponse(attachment, preSignedUri);
                })
                .toList();
    }

    public Optional<AttachmentResponse> getById(Long id) {
        Optional<Attachment> attachment = attachmentRepository.findById(id);

        if (attachment.isEmpty()) {
            return Optional.empty();
        }
        String preSignedUri = storageService.retrieve(attachment.get().getFileName());
        return Optional.of(AttachmentMapper.toResponse(attachment.get(), preSignedUri));
    }

    public Void deleteById(Long id) {
        Optional<Attachment> attachment = attachmentRepository.findById(id);
        if (attachment.isEmpty()) {
            throw new AttachmentNotFoundException("Attachment not found");
        }
        storageService.delete(attachment.get().getFileName());
        return null;
    }
}
