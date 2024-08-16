package org.mandulis.mts.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {

    private Long ticketId;
    private String content;
}
