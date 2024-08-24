package org.mandulis.mts.comment;

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
