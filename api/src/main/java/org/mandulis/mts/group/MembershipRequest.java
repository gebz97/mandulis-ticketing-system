package org.mandulis.mts.group;

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
