package org.mandulis.mts.security;

import org.mandulis.mts.entity.stale.Permission;
import org.mandulis.mts.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class WebUserDetails implements UserDetails {

    private final User user;

    public WebUserDetails(final User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Add permissions directly assigned to the user
        Set<Permission> permissions = new HashSet<>(user.getPermissions());

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
        return !user.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsExpireAt() == null ||
                user.getCredentialsExpireAt().after(new Date());
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
