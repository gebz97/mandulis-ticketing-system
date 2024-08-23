package org.mandulis.mts.attachment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentResponse {
    private Long id;
    private String fileName;
    private String uri;
    private Long ticketId;
}
