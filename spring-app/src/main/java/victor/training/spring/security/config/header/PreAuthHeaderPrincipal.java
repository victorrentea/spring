package victor.training.spring.security.config.header;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

// this object will later be available anywhere in the app
// for any flow starting with HTTP endpoint
public class PreAuthHeaderPrincipal implements UserDetails {
    private final String username;
    private final String sessionId;
    private final List<String> authorities;

    public PreAuthHeaderPrincipal(String username, String sessionId, List<String> roles) {
        this.username = username;
        this.sessionId = sessionId;
        this.authorities = roles.stream().map(s -> "ROLE_"+s).collect(toList());

        // expand incoming ROLE to AUTHORITIES
//        this.authorities = roles.stream()
//                .map(UserRole::valueOf)
//                .map(UserRole::getAuthorities)
//                .flatMap(Collection::stream)
//                .collect(toList());
    }

    public String getSessionId() {
        return sessionId;
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
