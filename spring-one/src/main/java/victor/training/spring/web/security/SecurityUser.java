//package victor.training.spring.web.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import victor.training.spring.web.domain.UserRole;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static java.util.stream.Collectors.toSet;
//
//public class SecurityUser implements UserDetails {
//	private final String username;
//	private final UserRole role;
//	private final Set<Long> managedTeacherIds;
//
//	SecurityUser(String username, UserRole role, Set<Long> managedTeacherIds) {
//		this.username = username;
//		this.role = role;
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
//		rolesAndAuthorities.add("ROLE_"+role.name());
//
//		rolesAndAuthorities.addAll(role.getAuthorities());
//
//		// NOTE: in practice you should choose ONE of the authorization models: ROLE-based or PERMISSION-based.
//
//		return rolesAndAuthorities.stream().map(SimpleGrantedAuthority::new).collect(toSet());
//	}
//
//	@Override
//	public String getPassword() {
//		return "{bcrypt}$2a$12$/UuQGk7atO1V62.VKK47.emjlqPgAO8oyiOBG71cEk3q4lO6zmbo."; // If persisting the user password, this returns the HASHED password stored in DB (prefixed with the hashing algo)
//      //means "macarena" bcrypted on https://bcrypt-generator.com/
//
////		return "{noop}" + username; // FAKE LOGIN---  assumes all users have password = username
//	}
//
//	public UserRole getRole() {
//		return role;
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
