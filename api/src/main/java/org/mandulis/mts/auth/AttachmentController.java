package org.mandulis.mts.controller;

import org.mandulis.mts.dto.AttachmentDto;
import org.mandulis.mts.exception.ProcessingMultipartFileException;
import org.mandulis.mts.service.AttachmentService;
import org.mandulis.mts.service.AttachmentValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
        var dto = new AttachmentDto(multipartFile.getOriginalFilename(), ticketId, toInputStream(multipartFile));
        attachmentValidationService.validateAttachment(multipartFile);
        attachmentService.save(dto);
        return ResponseEntity.status(201).build();
    }

    private InputStream toInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ProcessingMultipartFileException("File invalid.", e);
        }
    }

}
