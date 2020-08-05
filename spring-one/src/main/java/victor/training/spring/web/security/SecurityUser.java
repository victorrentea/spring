package victor.training.spring.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class SecurityUser implements UserDetails {
	private final long userId;
	private final String username;
	private final Set<String> permissions;
	private final Set<Long> managedTeacherIds;

	SecurityUser(long userId, String username, Set<String> permissions, Set<Long> managedTeacherIds) {
		this.userId = userId;
		this.username = username;
		this.permissions = permissions;
		this.managedTeacherIds = managedTeacherIds;
	}

	public long getUserId() {
		return userId;
	}

	public Set<Long> getManagedTeacherIds() {
		return managedTeacherIds;
	}

	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return permissions.stream().map(SimpleGrantedAuthority::new).collect(toSet());
	}

	@Override
	public String getPassword() {
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
		return "{noop}" + username; // FAKE LOGIN--- this assumes all users have password = username
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
	   // *** MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE MAGARIE
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
