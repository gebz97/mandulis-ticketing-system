package org.mandulis.mts.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private Long ticketId;
    private Long userId;
    private String content;
}
