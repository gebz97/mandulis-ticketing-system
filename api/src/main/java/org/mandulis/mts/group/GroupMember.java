package org.mandulis.mts.group;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMember {
    private Long id;
    private String name;
    private String email;
}
