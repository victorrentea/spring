//package victor.training.spring.web.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import victor.training.spring.web.domain.UserProfile;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static java.util.stream.Collectors.toSet;
//
//public class SecurityUser implements UserDetails {
//	private final String username;
//	private final UserProfile profile;
//	private final Set<Long> managedTeacherIds;
//
//	SecurityUser(String username, UserProfile profile, Set<Long> managedTeacherIds) {
//		this.username = username;
//		this.profile = profile;
//		this.managedTeacherIds = managedTeacherIds;
//	}
//
//	public Set<Long> getManagedTeacherIds() {
//		return managedTeacherIds;
//	}
//
//	@Override
//	public Set<GrantedAuthority> getAuthorities() {
//		List<String> rolesAndAuthorities = new ArrayList<>();
//
//		// @PreAuthorize("hasRole('X')) assumes the roles name begins with ROLE_ (eg ROLE_X)
//		rolesAndAuthorities.add("ROLE_"+profile.name());
//
//		rolesAndAuthorities.addAll(profile.authorities);
//
//		// NOTE: in practice you should choose ONE of the authorization models: ROLE-based or PERMISSION-based.
//
//		return rolesAndAuthorities.stream().map(SimpleGrantedAuthority::new).collect(toSet());
//	}
//
//	@Override
//	public String getPassword() {
////		return "{bcrypt}^$!&^&$!^@"; // If persisting the user password, this returns the HASHED password stored in DB (prefixed with the hashing algo)
//		return "{noop}" + username; // FAKE LOGIN---  assumes all users have password = username
//	}
//
//	public UserProfile getProfile() {
//		return profile;
//	}
//
//	@Override
//	public String getUsername() {
//		return username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//}
