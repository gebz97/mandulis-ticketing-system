package org.mandulis.mts.group;

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
