package org.mandulis.mts.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDetails {
    private Long id;
    private String name;
    private String description;
}