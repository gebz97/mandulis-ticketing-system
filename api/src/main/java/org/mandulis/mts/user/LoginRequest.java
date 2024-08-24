package org.mandulis.mts.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mandulis.mts.rest.ErrorMessages;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = ErrorMessages.LOGIN_USERNAME_VALIDATION)
    private String username;
    @NotBlank(message = ErrorMessages.PASSWORD_MANDATORY)
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
