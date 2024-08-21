package org.mandulis.mts.service;

import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.springframework.stereotype.Service;

@Service
public class AttachmentValidationService {

    public void validateAttachment(AttachmentDto attachmentDto) {
        if (attachmentDto.fileName == null || attachmentDto.fileName.isEmpty()) {
            throw new AttachmentWithoutNameException();
        }
    }
}
