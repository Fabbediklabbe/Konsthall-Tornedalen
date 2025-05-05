package com.grupp16.Tornedalen.Konsthall.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UserDetails;

import com.grupp16.Tornedalen.Konsthall.User;

import java.util.*;

public class CustomUserDetails implements UserDetails {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetails.class);

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // ingen rollhantering ännu
    }

    @Override
    public String getPassword() {
    logger.info("getPassword called – returning hash: {}", user.getPassword());
    return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // vi använder email som "username"
    }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}

