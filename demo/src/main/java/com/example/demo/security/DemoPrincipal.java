package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toSet;

public class DemoPrincipal implements UserDetails {
    private String username;
    private String fullName;
    private List<String> roles;

    public DemoPrincipal(String username, String fullName, List<String> roles) {
        this.username = username;
        this.fullName = fullName;
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(toSet());
    }

    @Override
    public String getPassword() {
        String noPasswordEncondingAlgo = "{noop}";
        return noPasswordEncondingAlgo + getUsername(); // hack de ultima speta: te vei putea loga cu parola == username
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
