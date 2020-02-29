package victor.training.spring.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class SecurityUser implements UserDetails {
	private final String username;
	private final Set<String> permissions;
	private final Set<Long> managedTeacherIds;

	SecurityUser(String username, Set<String> permissions, Set<Long> managedTeacherIds) {
		this.username = username;
		this.permissions = permissions;
		this.managedTeacherIds = managedTeacherIds;
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
		return "{noop}" + username; // FAKE LOGIN--- this assumes all users have password = username
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
