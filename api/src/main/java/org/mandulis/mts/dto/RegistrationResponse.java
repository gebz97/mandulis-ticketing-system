package org.mandulis.mts.dto;

public record RegistrationResponse(
   String username,
   String email,
   String firstName,
   String lastName
) {}
