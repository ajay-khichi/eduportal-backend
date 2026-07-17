package com.ignishers.backend.security;


import com.ignishers.backend.model.enums.AccountStatus;
import com.ignishers.backend.model.user.User;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final AccountStatus accountStatus;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.accountStatus = user.getAccountStatus();

        this.authorities = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getRoleName().name())
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user);
    }


    @Override
    @NonNull
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != AccountStatus.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return accountStatus == AccountStatus.ACTIVE;
    }
}
