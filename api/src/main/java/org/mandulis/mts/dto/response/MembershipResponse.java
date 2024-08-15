package org.mandulis.mts.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipResponse {
    private Long groupId;
    private Long userId;
}
