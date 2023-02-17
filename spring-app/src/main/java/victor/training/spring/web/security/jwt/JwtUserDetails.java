package victor.training.spring.web.security.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import victor.training.spring.web.entity.UserRole;

import java.util.Collection;

import static java.util.Arrays.asList;

@Data
// this object will later be available to any code needing it from the SecurityContextHolder
public class JwtUserDetails implements UserDetails {
    private final JwtToken jwtToken;

    public UserRole getRole() {
        return jwtToken.getRole();
    }

    @Override
    public String getUsername() {
        return jwtToken.getUsername();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return asList(() -> jwtToken.getRole().name());
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
