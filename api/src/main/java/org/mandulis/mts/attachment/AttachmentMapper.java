package org.mandulis.mts.attachment;

import org.mandulis.mts.attachment.views.MinimalAttachmentView;

public class AttachmentMapper {
    public static AttachmentResponse toResponse(Attachment attachment, String preSignedUri) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .preSignedUri(preSignedUri)
                .ticketId(attachment.getTicket().getId())
                .build();
    }

    public static AttachmentResponse minimalViewToResponse(MinimalAttachmentView view, String preSignedUri) {
        return  AttachmentResponse.builder()
                .id(view.getId())
                .fileName(view.getFileName())
                .preSignedUri(preSignedUri)
                .ticketId(view.getTicketId())
                .build();
    }
}