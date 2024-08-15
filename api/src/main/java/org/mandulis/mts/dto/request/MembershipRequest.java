package org.mandulis.mts.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipRequest {
    private Long userId;
    private Long groupId;
}
