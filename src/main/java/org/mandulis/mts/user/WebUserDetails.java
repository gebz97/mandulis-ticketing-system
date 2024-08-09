package org.mandulis.mts.user;

import org.mandulis.mts.security.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WebUserDetails implements UserDetails {

    private final User user;

    public WebUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Permission> permissions = new HashSet<>();

        // Add permissions directly assigned to the user
        permissions.addAll(user.getPermissions());

        // Add permissions associated with the user's role
        if (user.getRole() != null) {
            permissions.addAll(user.getRole().getPermissions());
        }

        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Customize based on your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Customize based on your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Customize based on your logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // Customize based on your logic
    }

    public User getUser() {
        return user;
    }
}
