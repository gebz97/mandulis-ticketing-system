package org.mandulis.mts.attachment;

import lombok.RequiredArgsConstructor;
import org.mandulis.mts.rest.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/attachment")
@RequiredArgsConstructor
class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AttachmentResponse>> attach(@RequestBody AttachmentRequest request) {
        return null;
    }
}
