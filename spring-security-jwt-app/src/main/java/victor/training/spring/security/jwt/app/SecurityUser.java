package victor.training.spring.security.jwt.app;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

@Data
public class SecurityUser implements UserDetails {
    private final String username;
    private final String fullName;
    private final String role;
    private String country; // dynamic, based on login request

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return asList(() -> role);
    }

    @Override
    public String getPassword() {
        return "{noop}"+username;
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
