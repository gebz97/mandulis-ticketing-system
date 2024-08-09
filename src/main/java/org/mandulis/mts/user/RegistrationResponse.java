package org.mandulis.mts.user;

public record RegistrationResponse(
   String username,
   String email,
   String firstName,
   String lastName
) {}
