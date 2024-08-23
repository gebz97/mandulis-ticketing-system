package org.mandulis.mts.attachment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentRequest {

    @NotBlank
    private String name;

    private Long ticketId;
    private String description;
    private String extension;
    private MultipartFile file;
}
