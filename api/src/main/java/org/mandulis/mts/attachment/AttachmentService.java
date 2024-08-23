package org.mandulis.mts.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mandulis.mts.exception.TicketNotFoundException;
import org.mandulis.mts.ticket.Ticket;
import org.mandulis.mts.ticket.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
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
}
