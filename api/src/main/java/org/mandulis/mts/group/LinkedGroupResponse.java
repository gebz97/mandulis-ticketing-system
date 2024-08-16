package org.mandulis.mts.group;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkedGroupResponse {
    private Long id;
    private String name;
    private List<GroupMember> members;
}
