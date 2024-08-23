package org.mandulis.mts.attachment;

import lombok.RequiredArgsConstructor;
import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
