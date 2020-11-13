package com.group7.banking.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtUserDetails 
        extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -6411988532329234916L;
    private long userId;

    public JwtUserDetails(String username, String password, long userId,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}