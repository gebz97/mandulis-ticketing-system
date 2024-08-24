package org.mandulis.mts.attachment;

import lombok.RequiredArgsConstructor;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ErrorMessages;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/attachment")
@ConditionalOnBean(name = "storageService")
@RequiredArgsConstructor
class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AttachmentResponse>> attach(
            @RequestBody AttachmentRequest request,
            @RequestParam("file") MultipartFile file) {
        return ResponseHandler.handleSuccess(
                attachmentService.saveAttachment(request, file),
                HttpStatus.CREATED,
                SuccessMessages.FILE_UPLOADED
        );
    }

    @GetMapping("/ticket-id={ticketId}")
    public ResponseEntity<ApiResponse<List<AttachmentResponse>>> getAttachmentsByTicketId(
            @PathVariable("ticketId") Long ticketId) {
        return ResponseHandler.handleSuccess(
                attachmentService.getAllAttachmentsByTicketId(ticketId),
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttachmentResponse>> getAttachmentById(@PathVariable("id") Long id) {
        return attachmentService.getById(id)
                .map(response -> ResponseHandler.handleSuccess(response,HttpStatus.OK,SuccessMessages.QUERY_SUCCESSFUL))
                .orElseGet(() -> ResponseHandler.handleError(null, HttpStatus.NOT_FOUND,
                        ErrorMessages.INVALID_ATTACHMENT_ID, List.of("Attachment not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttachmentById(@PathVariable("id") Long id) {
        return ResponseHandler.handleSuccess(
                attachmentService.deleteById(id),
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }
}
