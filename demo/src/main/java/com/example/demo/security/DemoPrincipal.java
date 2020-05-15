package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DemoPrincipal implements UserDetails {
    private String username;
    private String fullName;
    private List<String> roles;
    private final Set<Long> managedTeacherIds;

    public DemoPrincipal(String username, String fullName, List<String> roles, Set<Long> managedTeacherIds) {
        this.username = username;
        this.fullName = fullName;
        this.roles = roles;
        this.managedTeacherIds = managedTeacherIds;
    }

    public Set<Long> getManagedTeacherIds() {
        return managedTeacherIds;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> "ROLE_"+r).map(SimpleGrantedAuthority::new).collect(toSet());
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
