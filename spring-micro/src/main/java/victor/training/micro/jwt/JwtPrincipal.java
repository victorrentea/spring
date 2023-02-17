package victor.training.micro.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
// this object will later be available to any code needing it from the SecurityContextHolder
public class JwtPrincipal implements UserDetails {
	// data extracted from incoming JWT token
	private final String username;
	private final String country;
	private final String role;

	public String getRole() {
		return role;
	}

	// ------ below, SpringSecurity-mandated stuff -----

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return List.of(() -> "ROLE_" + role); // an authority is a role if it starts with ROLE_ 🤢
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
