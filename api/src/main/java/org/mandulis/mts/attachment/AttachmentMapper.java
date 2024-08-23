package org.mandulis.mts.attachment;

public class AttachmentMapper {
    public static AttachmentResponse toResponse(Attachment attachment, String preSignedUri) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .preSignedUri(preSignedUri)
                .ticketId(attachment.getTicket().getId())
                .build();
    }
}