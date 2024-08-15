package org.mandulis.mts.dto.response;

import lombok.*;
import org.mandulis.mts.dto.GroupMember;

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
