package org.mandulis.mts.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagRequest {
    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 1, max = 55, message = "Tag names must be between 1 and 55 characters")
    private String name;
    private String description;
}
