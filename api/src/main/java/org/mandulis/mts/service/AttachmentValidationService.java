package org.mandulis.mts.service;

import lombok.RequiredArgsConstructor;
import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.exception.AttachmentWithoutNameException;
import org.mandulis.mts.exception.EmptyAttachmentException;
import org.mandulis.mts.exception.TicketNotFoundException;
import org.mandulis.mts.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentValidationService {

    public void validateAttachment(MultipartFile multipartFile) {
        if(multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isEmpty()) {
            throw new AttachmentWithoutNameException();
        }

        if(multipartFile.isEmpty()) {
            throw new EmptyAttachmentException();
        }
    }
}
