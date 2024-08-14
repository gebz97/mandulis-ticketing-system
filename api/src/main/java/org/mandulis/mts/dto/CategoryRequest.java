package org.mandulis.mts.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String name;
    private String description;
}
