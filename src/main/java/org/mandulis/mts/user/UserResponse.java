package org.mandulis.mts.user;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String department;
    private List<String> groups;
    private String role;
    private String email;
}
