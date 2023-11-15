package victor.training.spring.security.config.preauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PreAuthPrincipal implements UserDetails {
    private final String username;
    private final List<String> authorities;

    public PreAuthPrincipal(String username, List<String> roles) {
        this.username = username;
        this.authorities = roles.stream().map(s -> "ROLE_"+s).collect(toList());

        // expand incoming ROLE to AUTHORITIES
//        this.authorities = roles.stream()
//                .map(UserRole::valueOf)
//                .map(UserRole::getAuthorities)
//                .flatMap(Collection::stream)
//                .collect(toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return "password";
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
