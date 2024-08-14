package org.mandulis.mts.dto;

import lombok.*;

import java.util.List;

@Data
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
