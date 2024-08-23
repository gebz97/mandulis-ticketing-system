package org.mandulis.mts.attachment;

public class AttachmentMapper {
    public static AttachmentResponse toResponse(Attachment attachment) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .uri(attachment.getUri())
                .ticketId(attachment.getTicket().getId())
                .build();
    }
}