package org.mandulis.mts.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mandulis.mts.rest.ErrorMessages;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = ErrorMessages.LOGIN_USERNAME_VALIDATION)
    private String username;
    @NotBlank(message = ErrorMessages.PASSWORD_MANDATORY)
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
