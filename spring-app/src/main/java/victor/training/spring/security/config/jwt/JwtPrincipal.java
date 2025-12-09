package victor.training.spring.security.config.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import victor.training.spring.web.entity.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
// this object will later be available to any code needing it from the SecurityContextHolder
public class JwtPrincipal implements UserDetails {
    // data extracted from incoming JWT token
    private final String username;
    private final String country;
    private final UserRole role;
    private final List<String> authorities;

    public UserRole getRole() {
        return role;
    }

    // ------ below, SpringSecurity-mandated stuff -----

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
//		return List.of(() -> "ROLE_" + role.name()); // an authority is a role if it starts with ROLE_ 🤢
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public String getPassword() {
        return null;
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
