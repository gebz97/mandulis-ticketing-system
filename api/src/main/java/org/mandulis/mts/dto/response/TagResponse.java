package org.mandulis.mts.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagResponse {
    private Long id;
    private String name;
    private String description;
}
