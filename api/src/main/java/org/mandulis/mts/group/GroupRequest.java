package org.mandulis.mts.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GroupRequest {
    @NotBlank(message = "Group name cannot be blank")
    @NotNull
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String name;
    private String description;
}
