package org.mandulis.mts.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mandulis.mts.exception.TicketNotFoundException;
import org.mandulis.mts.ticket.Ticket;
import org.mandulis.mts.ticket.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final StorageService storageService;
    private final TicketRepository ticketRepository;

    public AttachmentResponse saveAttachment(AttachmentRequest request) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(request.getTicketId());
        if (ticketOptional.isEmpty()) {
            throw new TicketNotFoundException("Ticket not found");
        }

        Ticket ticket = ticketOptional.get();
        AttachmentFile attachmentFile = AttachmentFile.builder()
                .fileName(request.getName())
                .ticketId(request.getTicketId())
                .inputStream(toInputStream(request.getFile()))
                .contentType(request.getFile().getContentType())
                .build();

        URI uri = storageService.store(attachmentFile);

        Attachment attachment = new Attachment();
        attachment.setFileName(request.getName());
        attachment.setUri(uri.toString());
        attachment.setTicket(ticket);

        attachmentRepository.save(attachment);

        return AttachmentMapper.toResponse(attachment);
    }

    private InputStream toInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error converting file", e);
        }
    }
}
