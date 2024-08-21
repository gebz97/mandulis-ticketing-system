package org.mandulis.mts.controller;

import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.exception.EmptyAttachmentException;
import org.mandulis.mts.service.AttachmentService;
import org.mandulis.mts.service.AttachmentValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user/tickets")
class AttachmentController {

    private final AttachmentValidationService attachmentValidationService;
    private final AttachmentService attachmentService;

    AttachmentController(AttachmentValidationService attachmentValidationService, AttachmentService attachmentService) {
        this.attachmentValidationService = attachmentValidationService;
        this.attachmentService = attachmentService;
    }

    @PostMapping("/{ticketId}/attachments")
    ResponseEntity<Void> upload(
            @PathVariable("ticketId") Long ticketId,
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        var attachmentDto = new AttachmentDto(multipartFile, ticketId);
        validateMultipart(multipartFile);
        attachmentValidationService.validateAttachment(attachmentDto);
        attachmentService.save(attachmentDto);
        return ResponseEntity.status(201).build();
    }

    private void validateMultipart(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            throw new EmptyAttachmentException();
        }
    }


}
